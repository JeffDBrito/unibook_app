import SidebarLink from "./SidebarLink";

export default function Sidebar() {
	return (
		<div
			style={{
				width: "220px",
				background: "#111827",
				color: "#fff",
				padding: "20px"
			}}
		>
			<h2 style={{ marginBottom: "20px" }}>UniBook</h2>

			<nav style={{ display: "flex", flexDirection: "column", gap: "10px" }}>
				<SidebarLink to="/dashboard">Dashboard</SidebarLink>
				<SidebarLink to="/books">Livros</SidebarLink>
				<SidebarLink to="/loans">Empréstimos</SidebarLink>
				<SidebarLink to="/users">Usuários</SidebarLink>
			</nav>
		</div>
	);
}