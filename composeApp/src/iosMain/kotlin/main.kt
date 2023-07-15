import androidx.compose.ui.window.ComposeUIViewController
import org.zold.app.App
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController {
    return ComposeUIViewController { App() }
}
