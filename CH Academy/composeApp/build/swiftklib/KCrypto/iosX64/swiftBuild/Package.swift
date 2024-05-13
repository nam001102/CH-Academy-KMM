// swift-tools-version:5.5
import PackageDescription

let package = Package(
	name: "KCrypto",
	products: [
		.library(
			name: "KCrypto",
			type: .static,
			targets: ["KCrypto"])
	],
	dependencies: [],
	targets: [
		.target(
			name: "KCrypto",
			dependencies: [],
			path: "KCrypto")
	]
)