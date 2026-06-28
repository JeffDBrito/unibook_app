import Sidebar from "./sidebar/Sidebar";
import Header from "./Header";

export default function AppLayout({ children, title }) {
	return (
		<div style={{ display: "flex", height: "100vh" }}>

			<Sidebar />

			<div style={{ flex: 1, display: "flex", flexDirection: "column" }}>
				<Header title={title} />

				<main style={{ padding: "20px", background: "#f5f5f5", flex: 1 }}>
					{children}
				</main>
			</div>

		</div>
	);
}