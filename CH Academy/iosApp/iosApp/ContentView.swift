import UIKit
import SwiftUI
import shared

struct ComposeView: UIViewControllerRepresentable, UIResponder, UIApplicationDelegate {

    var window: UIWindow?

    func makeUIViewController(context: Context) -> UIViewController {
        let firestoreListener = UserViewModelIosApp()
        ComposeAppKt.setupFirestoreListener(listener: firestoreListener)

        return MainViewControllerKt.MainViewController()
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}


}

struct ContentView: View {
    var body: some View {
        ComposeView()
                .ignoresSafeArea(.keyboard) // Compose has own keyboard handler
    }
}



