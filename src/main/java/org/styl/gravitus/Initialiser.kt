package org.styl.gravitus

import org.apache.log4j.Logger
import org.styl.gravitus.ui.View

object Initialiser {
    private val logger: Logger = Logger.getLogger(Initialiser::class.java)

    @JvmStatic
    fun main(args: Array<String>) {
        logger.info("starting Application")

        // initialize properties
        SpecsLoader()

        val runner = Runner()
        val view = View()
        val ctrl = Controller(view, runner)
        ctrl.init(runner.getStageList())

        view.isVisible = true
        ctrl.startSimulation()
    }
}
