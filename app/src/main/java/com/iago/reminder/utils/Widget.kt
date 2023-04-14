package com.iago.reminder.utils

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.glance.*
import androidx.glance.action.ActionParameters
import androidx.glance.action.actionParametersOf
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.action.ActionCallback
import androidx.glance.appwidget.action.actionRunCallback
import androidx.glance.appwidget.cornerRadius
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.glance.appwidget.updateAll
import androidx.glance.layout.*
import androidx.glance.state.GlanceStateDefinition
import androidx.glance.state.PreferencesGlanceStateDefinition
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import androidx.room.Room
import com.iago.reminder.R
import com.iago.reminder.database.ReminderDatabase
import com.iago.reminder.repository.ReminderRepository
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.io.File


private val wordPreferenceKey = stringPreferencesKey("word")
private val wordParamKey = ActionParameters.Key<String>("word")
private val translatePreferenceKey = stringPreferencesKey("translate")
private val translateParamKey = stringPreferencesKey("translate")


object CustomGlanceStateDefinition : GlanceStateDefinition<Preferences> {
    override suspend fun getDataStore(context: Context, fileKey: String): DataStore<Preferences> {
        return context.dataStore
    }

    override fun getLocation(context: Context, fileKey: String): File {
        return File(context.applicationContext.filesDir, "datastore/$fileName")
    }

    private const val fileName = "widget_store"
    private val Context.dataStore: DataStore<Preferences>
            by preferencesDataStore(name = fileName)
}

class Widget : GlanceAppWidget() {

    override val stateDefinition = CustomGlanceStateDefinition

    @Composable
    override fun Content() {

        val context = LocalContext.current
        val prefs = currentState<Preferences>()

        var word = prefs[wordPreferenceKey] ?: ""
        var translate = prefs[translatePreferenceKey] ?: ""

        Column(
            verticalAlignment = Alignment.CenterVertically,
            modifier = GlanceModifier.fillMaxSize().background(Color(0xFF4E40D9)).padding(10.dp)
        ) {
            if (word.isEmpty()) {
                StartQuiz(context)
            } else
                GuessWord(word, translate)
        }
    }

    @Composable
    fun StartQuiz(context: Context) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = GlanceModifier.fillMaxWidth(),
        ) {
            Row(
                modifier = GlanceModifier.defaultWeight(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextWidget(
                    text = context.getString(R.string.start_quiz),
                    modifier = GlanceModifier.clickable(
                        actionRunCallback<UpdateWordActionCallback>()
                    )
                )
                Image(
                    contentDescription = null,
                    modifier = GlanceModifier.padding(start = 5.dp)
                        .clickable(
                        actionRunCallback<UpdateWordActionCallback>()
                    ),
                    provider = ImageProvider(R.drawable.ic_play),
                )
            }
        }
    }

    @Composable
    fun GuessWord(word: String, translate: String) {
        Header(word)
        Spacer(modifier = GlanceModifier.fillMaxWidth().height(5.dp))
        if (translate.isEmpty())
            HideText(word)
        else
            TranslateText(translate)
    }

}

@Composable
fun TranslateText(translate: String) {
    Row(modifier = GlanceModifier.height(40.dp)) {
        TextWidget(
            translate,
            fontWeight = FontWeight.Normal,
            modifier = GlanceModifier.padding(start = 5.dp)
        )
    }
}

@Composable
fun Header(word: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = GlanceModifier.fillMaxWidth()
    ) {
        Row(modifier = GlanceModifier.defaultWeight()) {
            TextWidget(word, modifier = GlanceModifier.padding(start = 5.dp))
        }
        Row(modifier = GlanceModifier.defaultWeight(), horizontalAlignment = Alignment.End) {
            Image(
                contentDescription = null,
                modifier = GlanceModifier.padding(10.dp)
                    .clickable(actionRunCallback<UpdateWordActionCallback>()),
                provider = ImageProvider(R.drawable.ic_refresh),
            )
        }
    }
}

@Composable
fun HideText(word: String) {
    Row(
        modifier = GlanceModifier.fillMaxWidth().height(40.dp).cornerRadius(5.dp)
            .background(Color(0xFF4336C0)).clickable(
                actionRunCallback<UpdateTranslateActionCallback>(
                    parameters = actionParametersOf(
                        wordParamKey to word
                    )
                )
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            contentDescription = null,
            modifier = GlanceModifier.padding(horizontal = 10.dp, vertical = 5.dp),
            provider = ImageProvider(R.drawable.ic_eye),
        )
    }
}

@Composable
fun TextWidget(
    text: String,
    size: TextUnit = 18.sp,
    fontWeight: FontWeight = FontWeight.Bold,
    modifier: GlanceModifier = GlanceModifier,
) {
    Text(
        modifier = modifier,
        maxLines = 1,
        text = text,
        style = TextStyle(
            fontSize = size,
            fontWeight = fontWeight,
            color = ColorProvider(color = Color.White)
        ),
    )
}

fun getRepository(context: Context): ReminderRepository {
    return ReminderRepository(
        Room.databaseBuilder(context, ReminderDatabase::class.java, "reminder_db").build()
            .getDao()
    )
}

suspend fun updateWord(context: Context, word: String) {
    GlanceAppWidgetManager(context).getGlanceIds(Widget::class.java).forEach { glanceId ->
        updateAppWidgetState(context, glanceId) { prefs ->
            prefs[stringPreferencesKey("word")] = word
        }
    }
    Widget().updateAll(context)
}

private suspend fun updateWidget(
    context: Context,
    glanceId: GlanceId,
    key: Preferences.Key<String>,
    value: String
) {
    updateAppWidgetState(
        context = context,
        definition = PreferencesGlanceStateDefinition,
        glanceId = glanceId
    ) { preferences ->
        preferences.toMutablePreferences()
            .apply {
                this[key] = value
            }
    }

    Widget().update(context, glanceId)
}

class UpdateTranslateActionCallback : ActionCallback {
    override suspend fun onAction(
        context: Context,
        glanceId: GlanceId,
        parameters: ActionParameters
    ) {

        val word = requireNotNull(parameters[wordParamKey])

        val coroutineScope = MainScope()
        val repository = getRepository(context)

        coroutineScope.launch {
            val words = repository.getWords()
            if (words.isNotEmpty()) {
                val wordSelected = words.first { it.word == word }
                updateWidget(context, glanceId, translatePreferenceKey, wordSelected.word_translate)

            }
        }
    }

}


class UpdateWordActionCallback : ActionCallback {
    override suspend fun onAction(
        context: Context,
        glanceId: GlanceId,
        parameters: ActionParameters
    ) {

        val coroutineScope = MainScope()
        val repository = getRepository(context)

        coroutineScope.launch {
            val words = repository.getWords()
            if (words.isNotEmpty()) {

                val word = words.random()
                updateWidget(context, glanceId, wordPreferenceKey, word.word)
                updateWidget(
                    context,
                    glanceId,
                    translateParamKey,
                    ""
                )

            }
        }
    }
}