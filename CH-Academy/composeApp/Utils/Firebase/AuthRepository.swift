import Foundation
import Firebase

@objc class IOSAuthRepository: NSObject {

    @objc func requestOTP(phoneNumber: String, onSuccess: @escaping (String) -> Void, onError: @escaping (NSError) -> Void) {
        PhoneAuthProvider.provider().verifyPhoneNumber(phoneNumber, uiDelegate: nil) { verificationID, error in
            if let error = error as NSError? {
                onError(error)
            } else {
                onSuccess(verificationID ?? "")
            }
        }
    }

    @objc func verifyOTP(verificationId: String, code: String, onSuccess: @escaping () -> Void, onError: @escaping (NSError) -> Void) {
        let credential = PhoneAuthProvider.provider().credential(withVerificationID: verificationId, verificationCode: code)
        Auth.auth().signIn(with: credential) { _, error in
            if let error = error as NSError? {
                onError(error)
            } else {
                onSuccess()
            }
        }
    }
}
