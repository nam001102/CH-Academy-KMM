import composeApp
import Firebase

func setupSnapshotListener(userId: String) -> UserData {

    private let db = Firebase.firestore
    private let userDataDocument = db.collection("users").document(userId)
    private let userStatsDocument = userDataDocument.collection("Stats").document("State")

    userDataDocument.addSnapshotListener(includeMetadataChanges: true) { documentSnapshot, error in
        guard let document = documentSnapshot else {
            print("Error fetching document: \(error!)")
            return
        }
        let source = document.metadata.hasPendingWrites ? "Local" : "Server"
        return UserData(

        )
        print("\(source) data: \(document.data() ?? [:])")
    }
}