package bernardo.bernardinhio.kotlinclasstypesInbankingapp.view

import android.app.Activity
import android.graphics.Point
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.view.View

import bernardo.bernardinhio.kotlinclasstypesInbankingapp.R

class DashboardActivityBank : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dashboard_activity_bank)

        setActivityDimensions()
    }

    private fun setActivityDimensions(){
        val display = windowManager.defaultDisplay
        val point = Point()
        display.getSize(point)
        val screenWidth = point.x
        val screenHeight = point.y

        val distanceToDeduce = (screenWidth * 0.17).toInt()
        val activityContainerLayoutParams = findViewById<ConstraintLayout>(R.id.cl_container_activity_dashboard_bank).layoutParams
        activityContainerLayoutParams.width = screenWidth - distanceToDeduce
        activityContainerLayoutParams.height = screenHeight - distanceToDeduce
    }

    fun closeActivityBank(view : View){
        this.finish()
    }
}
