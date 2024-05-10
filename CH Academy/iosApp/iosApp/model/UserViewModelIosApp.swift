import Foundation
import Firebase
import shared
import Combine
import FirebaseFirestore


class UserViewModelIosApp : NSObject, UserViewModel {

    private let db = Firebase.firestore

    @Published var userData: UserData

    init() {
            self.userData = UserData() // Initialize with empty data or default values
            setupSnapshotListeners()
    }

    override func setupSnapshotListener(userId: String)  {

        fetchUserData(userId){  userData ->
            self.userData = userData
        }

        let userDataDocument = db.collection("users").document(userId)
        let userStatsDocument = userDataDocument.collection("Stats").document("State")

        userDataDocument.addSnapshotListener { userSnapshot, error in
            if let error = error {
                print("Error fetching user document: \(error)")
                return
            }

            guard let userSnapshot = userSnapshot, userSnapshot.exists else {
                print("User document does not exist")
                return
            }

            // Extract user data from the snapshot
            let userData = UserData(
                avatar: userSnapshot.get("avatar") as? String ?? "",
                background: userSnapshot.get("background") as? String ?? "",
                phone: userSnapshot.get("phone") as? String ?? userId,
                name: userSnapshot.get("name") as? String ?? "",
                date: userSnapshot.get("date") as? String ?? "",
                point: userSnapshot.get("point") as? Int ?? 0,
                sex: userSnapshot.get("sex") as? Int64 ?? 2,
                bio: userSnapshot.get("bio") as? String ?? "",
                unlockedVideos: userSnapshot.get("videoImageList") as? [String] ?? []
            )

            self.userData = userData
        }

        userStatsDocument.addSnapshotListener { statsSnapshot, error in
            if let error = error {
                print("Error fetching stats document: \(error)")
                return
            }

            guard let statsSnapshot = statsSnapshot, statsSnapshot.exists else {
                print("Stats document does not exist")
                return
            }

            // Extract user stats from the snapshot
            let userStats = UserStats(
                Life: statsSnapshot.get("Life") as? Bool ?? true,
                Destiny: statsSnapshot.get("Destiny") as? Bool ?? false,
                Connection: statsSnapshot.get("Connection") as? Bool ?? false,
                Growth: statsSnapshot.get("Growth") as? Bool ?? false,
                Soul: statsSnapshot.get("Soul") as? Bool ?? false,
                Personality: statsSnapshot.get("Personality") as? Bool ?? false,
                Balance: statsSnapshot.get("Balance") as? Bool ?? false,
                RationalThinking: statsSnapshot.get("RationalThinking") as? Bool ?? false,
                MentalPower: statsSnapshot.get("MentalPower") as? Bool ?? false,
                DayOfBirth: statsSnapshot.get("DayOfBirth") as? Bool ?? false,
                Passion: statsSnapshot.get("Passion") as? Bool ?? false,
                MissingNumbers: statsSnapshot.get("MissingNumbers") as? Bool ?? false,
                PersonalYear: statsSnapshot.get("PersonalYear") as? Bool ?? false,
                PersonalMonth: statsSnapshot.get("PersonalMonth") as? Bool ?? false,
                PersonalDay: statsSnapshot.get("PersonalDay") as? Bool ?? false,
                Phrase: statsSnapshot.get("Phrase") as? Bool ?? false,
                Challange: statsSnapshot.get("Challange") as? Bool ?? false,
                Agging: statsSnapshot.get("Agging") as? Bool ?? false
            )

            self.userData = userStats
        }
    }

    func fetchUserData(userId: String) -> AnyPublisher<UserData?, Error> {
        return Future<UserData?, Error> { promise in
            DispatchQueue.global().async {
                do {
                    // Perform asynchronous operation to fetch user data
                    let user = try getUsersById(userId)

                    // Return user data via promise
                    DispatchQueue.main.async {
                        promise(.success(user))
                    }
                } catch {
                    // Handle errors
                    print("Error fetching user data: \(error)")
                    DispatchQueue.main.async {
                        promise(.failure(error))
                    }
                }
            }
        }
        .eraseToAnyPublisher()
    }

    override func updateStatsStatus(userId: String, statsId: String, newStatus: Bool, completion: @escaping (Error?) -> Void) {
        let db = Firestore.firestore()
        let userDocument = db.collection("users").document(userId)
        let statsDocument = userDocument.collection("Stats").document("State")

        statsDocument.updateData([
            statsId: newStatus
        ]) { error in
            if let error = error {
                // Handle error
                print("Error updating stats status: \(error)")
                completion(error)
            } else {
                // Update successful
                completion(nil)
            }
        }
    }
}