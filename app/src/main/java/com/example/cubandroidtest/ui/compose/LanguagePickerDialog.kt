import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.cubandroidtest.R

@Composable
fun LanguagePickerDialog(
    selectedLanguage: String,
    onDismiss: () -> Unit,
    onLanguageSelected: (String) -> Unit,
) {
    val languages = listOf("ar", "de", "en", "es", "fr", "he", "it", "nl", "no", "pt", "ru", "sv", "ud", "zh")

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(stringResource(R.string.home_select_language)) },
        text = {
            LazyColumn {
                items(languages.size) { index ->
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .clickable {
                                // Handle language selection
                                onLanguageSelected(languages[index])
                                onDismiss()  // Dismiss the dialog after selection
                            }
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(languages[index].uppercase(), Modifier.weight(1f))
                        if (languages[index] == selectedLanguage) {
                            Icon(Icons.Default.Check, contentDescription = "Selected")
                        }
                    }
                }
            }
        },
        confirmButton = {}
    )
}
