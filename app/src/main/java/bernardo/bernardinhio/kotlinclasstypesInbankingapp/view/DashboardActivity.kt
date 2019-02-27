package bernardo.bernardinhio.kotlinclasstypesInbankingapp.view

import android.app.AlertDialog
import android.graphics.Point
import android.support.constraint.ConstraintLayout
import android.support.v4.app.FragmentActivity
import android.view.View
import bernardo.bernardinhio.kotlinclasstypesInbankingapp.R

open class DashboardActivity : FragmentActivity(){ // should be FragmentActivity to allow floating popop effect

    // comon to all dashboard activities
    protected fun setActivityDimensions(){
        val display = windowManager.defaultDisplay
        val point = Point()
        display.getSize(point)
        val screenWidth = point.x
        val screenHeight = point.y

        val distanceToDeduce = (screenWidth * 0.17).toInt()
        val activityContainerLayoutParams = findViewById<ConstraintLayout>(R.id.cl_container_activity_dashboard).layoutParams
        activityContainerLayoutParams.width = screenWidth - distanceToDeduce
        activityContainerLayoutParams.height = screenHeight - distanceToDeduce
    }

    // common to all dashboard activities button close same id
    fun closeDashboardActivity(view : View){
        confirmLeavingActivity()
    }

    // common to all dashboard activities
    override fun onBackPressed() {
        confirmLeavingActivity()
    }

    // common to all dashboard activities
    open protected fun confirmLeavingActivity() : Boolean{
        var doFinishActivity = false
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Sure you want to stop?")
        builder.setMessage("If you stop it, all the information recorded will be lost")
        builder.setPositiveButton("YES") { dialog, which ->
            this.finish()
            doFinishActivity = true
        }
        builder.setNeutralButton("CANCEL") {
            dialog, which ->
            builder.setCancelable(true)
        }
        builder.show()
        return doFinishActivity
    }
}