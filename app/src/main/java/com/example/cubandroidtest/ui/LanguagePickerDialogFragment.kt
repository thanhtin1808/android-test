import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.DialogFragment

class LanguagePickerDialogFragment(
    private var selectedLanguage: String,
    private val onLanguageSelected: (String) -> Unit,
) : DialogFragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                LanguagePickerDialog(
                    selectedLanguage = selectedLanguage,
                    onDismiss = { dismiss() },
                    onLanguageSelected = { language ->
                        selectedLanguage = language
                        onLanguageSelected(language)
                        dismiss()
                    })
            }
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT
        )
    }
}
