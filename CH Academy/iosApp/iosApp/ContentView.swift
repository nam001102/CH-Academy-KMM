import Foundation
import SwiftUI
import ComposeApp

struct ComposeView: UIViewControllerRepresentable {

    var window: UIWindow?

    func makeUIViewController(context: Context) -> UIViewController {

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



