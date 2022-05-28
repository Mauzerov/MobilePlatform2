package com.mauzerov.mobileplatform2.engine.threding

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.*
import android.util.Log
import android.util.Size
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import com.mauzerov.mobileplatform.items.ItemDrawable
import com.mauzerov.mobileplatform2.R
import com.mauzerov.mobileplatform2.adapter.controller.Dimensions
import com.mauzerov.mobileplatform2.adapter.controller.JoyStick
import com.mauzerov.mobileplatform2.engine.drawing.Textures
import com.mauzerov.mobileplatform2.engine.files.FileSystem
import com.mauzerov.mobileplatform2.engine.files.GameSave
import com.mauzerov.mobileplatform2.engine.files.inner.BuildingSave
import com.mauzerov.mobileplatform2.engine.files.inner.PlayerSave
import com.mauzerov.mobileplatform2.entities.living.LivingEntity
import com.mauzerov.mobileplatform2.entities.living.Player
import com.mauzerov.mobileplatform2.extensions.*
import com.mauzerov.mobileplatform2.include.Biome
import com.mauzerov.mobileplatform2.include.Height
import com.mauzerov.mobileplatform2.include.Position
import com.mauzerov.mobileplatform2.sprites.buildings.Building
import com.mauzerov.mobileplatform2.sprites.buildings.Clickable
import com.mauzerov.mobileplatform2.sprites.buildings.MissionBuilding
import com.mauzerov.mobileplatform2.values.const.GameConstants.RefreshInterval
import com.mauzerov.mobileplatform2.values.const.GameConstants.biomeMap
import com.mauzerov.mobileplatform2.values.const.GameConstants.doubleTileHeight
import com.mauzerov.mobileplatform2.values.const.GameConstants.doubleTileWidth
import com.mauzerov.mobileplatform2.values.const.GameConstants.heightMap
import com.mauzerov.mobileplatform2.values.const.GameConstants.mapSize
import com.mauzerov.mobileplatform2.values.const.GameConstants.oceanDepth
import com.mauzerov.mobileplatform2.values.const.GameConstants.tileSize
import java.io.File

@SuppressLint("ViewConstructor")
class GameView(private val context: Activity, private val filePath: String):
    SurfaceView(context),
    SurfaceHolder.Callback,
    View.OnTouchListener,
    JoyStick.JoystickListener
{
    private val buildings: MutableList<Building> = mutableListOf()
    var finished: Boolean = false
    private val textures = Textures(context.resources)

    private class UpdateThread(private val gameView: GameView) : Thread() {
        var isRunning = false

        @SuppressLint("WrongCall")
        override fun run() {
            while (isRunning) {
                var canvas: Canvas? = null
                try {
                    val startTime = System.currentTimeMillis()
                    canvas = gameView.holder.lockCanvas()
                    for (entity in gameView.entities) {
                        entity.move()
                    }
                    synchronized(gameView.holder) { gameView.onDraw(canvas) }
                    val duration = System.currentTimeMillis() - startTime

                    gameView.player.selectedItem?.let {
                        if (it is ItemDrawable && it.isShowed) {
                            it.drawMySelf(canvas)
                        }
                    }

                    sleep(0L.coerceAtLeast(RefreshInterval - duration))
                } finally {
                    if (canvas != null) gameView.holder.unlockCanvasAndPost(canvas)
                }
            }
        }
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        if (thread.state == Thread.State.TERMINATED) {
            thread = UpdateThread(this@GameView)
        }
        thread.isRunning = true
        thread.start()
    }

    override fun surfaceCreated(holder: SurfaceHolder) {}

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        var retry = true
        thread.isRunning = false
        saveStateToFile()
        while (retry) {
            try {
                thread.join()
                retry = false
            } catch (ignored : InterruptedException) {}
        }
    }

    val player: Player = Player(0, 0, 32, 32).apply {
        this.onHealthChanged = { value, max ->
            Log.d("Heart", "Override")
            this@GameView.gameBar. updateHearts(value, max, 5)
        }
    }
    val entities: MutableList<LivingEntity> = mutableListOf()

    private var thread: UpdateThread = UpdateThread(this)
    private var gameBar = GameBar(context, this)

    private fun loadStateFromFile() {
        val saveObject: GameSave = FileSystem.readObject(context, filePath) as GameSave
        player.loadFromSaveObject(saveObject.playerSave)
    }

    private fun saveStateToFile() {
        val saveObject = GameSave(
            playerSave = PlayerSave(
                positionX = player.position.x,
                positionY = player.position.y,
                health = player.health,
                money = player.money,
                ),
            buildingSav = BuildingSave()
        )
        FileSystem.writeObject(context, filePath, saveObject)
    }

    init {
        holder.addCallback(this)
        entities.add(player)

        setOnTouchListener(this)

        buildings.add(object: MissionBuilding(), Clickable {
            override val missionId: Int = 1337
            override val roof: Bitmap  = createStaticColorBitmap(tileSize.width, tileSize.width, Color.DKGRAY)
            override val story: Bitmap = createStaticColorBitmap(tileSize.width, tileSize.width, 0x50FF0000.toInt())
            override val floor: Bitmap = createStaticColorBitmap(tileSize.width, tileSize.width, 0x5000FF00.toInt())
            override val position: Point = Point(104, 0)
            override val size: Point = Point(2, 5)

            override fun onClick(position: Point) : Boolean {
                if (!this.collides(position.x))
                    return false
                Log.d("Mission", "$missionId")

                return true
            }
        })

        // if save file exists load state from it
        if (File(context.filesDir, filePath).exists())
            loadStateFromFile()
        player.hit(0)
    }
    private fun getTiles(): Int = (width / doubleTileWidth)
    private fun getDrawLeft() : Int = player.position.x / tileSize.width - getTiles() - 1
    private fun getDrawRight() : Int = player.position.x / tileSize.width + getTiles() + 1

    public override fun onDraw(g: Canvas?) {
        fun getMinPossibleHeight(pos: Position): Int {
            val mapX = ((pos.x) / tileSize.width)

            if (mapX in 0 until mapSize && pos.x.between(0, mapSize * tileSize.width))
                return heightMap[mapX].ground * tileSize.height
            return -oceanDepth * 4
        }

        fun adjustYPosition(pos: Position) {
            pos.y = pos.y.coerceAtLeast(getMinPossibleHeight(pos))
        }

        fun drawPlayer(g: Canvas) {
            adjustYPosition(player.position)
            GameViewCanvasConfig.screenOffsetX = 0F
            GameViewCanvasConfig.screenOffsetY = player.size.height.toFloat()
            g.gameDrawRect(
                (width / 2) - (player.size.width / 2),
                (player.position.y + doubleTileHeight),
                player.size.width, player.size.height,
                Color.MAGENTA
            )
        }

        val tiles = (width / doubleTileWidth)
        val noTile = (width % tileSize.width)
        var drawX = (noTile shr 1) - tileSize.width
        val left = getDrawLeft()
        val right = getDrawRight()
        g?.let {
            GameViewCanvasConfig.screenOffsetX = 0F
            GameViewCanvasConfig.screenOffsetY = 0F
            GameViewCanvasConfig.screenHeight = 0F
            g.gameDrawBitmap(textures.sky, 0, 0)
            GameViewCanvasConfig.screenOffsetX = (player.position.x % tileSize.width).toFloat()
            GameViewCanvasConfig.screenOffsetY = doubleTileHeight.toFloat()
            GameViewCanvasConfig.screenHeight = height.toFloat()
            for (i in left..right) {
                if (i !in 0 until mapSize || (biomeMap[i] == Biome.Ocean)) {
                    // Generate Ocean
                    g.gameDrawRect(drawX, -oceanDepth, tileSize.width, doubleTileHeight, 0xFF16a085.toInt())
                }
                else {
                    if (heightMap[i].isTunnel()) {
                        for (j in -1 .. heightMap[i].ground) {
                            // Generate Ground
                            g.gameDrawBitmap(textures.ground.dirt, drawX, j * tileSize.height)
                        }
                        for (j in heightMap[i].ground + 1 .. heightMap[i].ground + Height.tunnelHeight) {
                            // Generate Further Dirt Wall
                            g.gameDrawBitmap(textures.ground.wall, drawX, j * tileSize.height)
                        }
                        for (j in 1 + heightMap[i].ground + Height.tunnelHeight .. heightMap[i].actual) {
                            // Generate Ground
                            g.gameDrawBitmap(textures.ground.dirt, drawX, j * tileSize.height)
                        }
                        g.gameDrawBitmap(textures.ground.concrete, drawX, heightMap[i].ground * tileSize.height)

                        // Draw Grass
                        g.gameDrawBitmap(textures.ground.grass, drawX, heightMap[i].actual * tileSize.height)

                    }
                    else if (!heightMap[i].isSame()) {
                        for (j in -1 .. heightMap[i].ground) {
                            // Generate Ground
                            g.gameDrawBitmap(textures.ground.dirt, drawX, j * tileSize.height)
                        }
                        for (j in 1 + heightMap[i].ground .. heightMap[i].actual) {
                            // Generate Further Dirt Wall
                            g.gameDrawBitmap(textures.ground.dirt2, drawX, j * tileSize.height)
                        }

                        g.gameDrawBitmap(textures.ground.concrete, drawX, heightMap[i].ground * tileSize.height)

                        // Draw Grass
                        g.gameDrawBitmap(textures.ground.grass3, drawX, heightMap[i].actual * tileSize.height)

                    }
                    else {
                        for (j in -1 .. heightMap[i].actual) {
                            // Generate Ground
                            g.gameDrawBitmap(textures.ground.dirt, drawX, j * tileSize.height)
                        }

                        // Draw Grass
                        when (biomeMap[i]) {
                            Biome.City, Biome.Airport, Biome.Docs ->
                                g.gameDrawBitmap(textures.ground.concrete, drawX, heightMap[i].actual * tileSize.height)
                            else ->
                                g.gameDrawBitmap(textures.ground.grass, drawX, heightMap[i].actual * tileSize.height)
                        }

                    }

                    if (biomeMap[i] == Biome.Forest) {
                        // Draw Tree
                        g.gameDrawBitmap(textures.tree.spruce, drawX, heightMap[i].actual * tileSize.height + 128)

                        if (biomeMap.elementAtOrNull(i + 1) == Biome.Forest) {
                            if (heightMap[i] == heightMap[i + 1]) {
                                // Draw More Tree
                                g.gameDrawBitmap(textures.tree.spruce,
                                    drawX + (tileSize.width shr 1),
                                    heightMap[i].actual * tileSize.height + 128)
                            }
                        }
                    }

                    g.gameDrawText(i.toString(),
                        drawX,
                        (heightMap[i].actual * tileSize.height),
                        Color.BLACK,
                        40f
                    )
                    g.gameDrawText(biomeMap[i].toString(),
                        drawX,
                        (heightMap[i].actual * tileSize.height)+tileSize.height,
                        Color.BLACK,
                        20f
                    )

                }
                drawX += tileSize.width
            }

            GameViewCanvasConfig.screenOffsetX -= (noTile shr 1) - tileSize.width
            buildings.filter { building -> building.fits(left - 1, right) }.forEach { building ->
                building.draw(it, left)

                if (building is MissionBuilding) {
                    it.gameDrawBitmap(
                        textures.questionMark,
                        (building.position.x - left) * tileSize.width,
                        height - 160
                    )
                }
            }
            drawPlayer(g)

            gameBar.onDraw(it)
        }
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        Log.d("Point", "x=${event?.x}; y=${event?.y}")
        event?.let {
            val offsetToCenter = event.x.toInt() - (width / 2 - (player.size.width / 2))
            val pressedX = player.position.x + offsetToCenter
            val mapIndex = pressedX / tileSize.width

            if (buildings.filter { it.fits(getDrawLeft(), getDrawRight()) }
                .filterIsInstance<Clickable>()
                .any { it.onClick(Point(mapIndex, event.y.toInt())) }
            ) return true
        }
        return gameBar.onTouchEvent(event)
    }

    override fun onJoystickMoved(percent: Dimensions, id: Int) {
        player.position.setVelocity(percent.x.times(7).toInt(), null)
    }
}