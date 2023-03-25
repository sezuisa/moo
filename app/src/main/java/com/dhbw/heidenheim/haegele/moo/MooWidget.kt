package com.dhbw.heidenheim.haegele.moo

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.widget.ImageView
import android.widget.RemoteViews
import androidx.core.content.edit
import androidx.lifecycle.lifecycleScope
import com.dhbw.heidenheim.haegele.moo.data.SyncRealmController
import com.google.android.material.color.MaterialColors
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * Implementation of App Widget functionality.
 */
class MooWidget : AppWidgetProvider() {
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }

    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)

        val action = intent!!.action ?: ""
        val syncRealmController = SyncRealmController()
        val repository = syncRealmController.getRepo()
        if (context != null && action == "addHappyCard") {
            Log.d("Tag", "Widget Clicked")

            GlobalScope.launch {
                repository.addCard("","","happy")

            }
        }
        if (context != null && action == "addUnhappyCard") {
            Log.d("Tag", "Widget Clicked")

            GlobalScope.launch {
                repository.addCard("","","unhappy")

            }
        }
        if (context != null && action == "addNeutralCard") {
            Log.d("Tag", "Widget Clicked")


            GlobalScope.launch {
                repository.addCard("","","neutral")

            }
        }



    }
    private fun updateAppWidget(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetId: Int
    ) {
        // Create an Intent to launch ExampleActivity

        // Get the layout for the widget and attach an on-click listener
        // to the button.
        val views = RemoteViews(context.packageName, R.layout.moo_widget)
        views.setOnClickPendingIntent(R.id.wbtnHappy, pendingIntent(context, "addHappyCard"))
        views.setOnClickPendingIntent(R.id.wbtnNeutral, pendingIntent(context, "addNeutralCard"))
        views.setOnClickPendingIntent(R.id.wbtnSad, pendingIntent(context, "addUnhappyCard"))
        // Tell the AppWidgetManager to perform an update on the current
        // widget.
        appWidgetManager.updateAppWidget(appWidgetId, views)

    }

    private fun pendingIntent(context: Context?, action: String): PendingIntent? {
        val intent = Intent(context, javaClass)
        intent.action = action
        return PendingIntent.getBroadcast(context, 0 , intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE)

    }
}


