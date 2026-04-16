import { useAuth } from "../../hooks/useAuth";
import { useNavigate } from "react-router-dom";

export default function Header({ title }) {
	const { logout } = useAuth();
	const navigate = useNavigate();

  function handleLogout() {
    logout();
    navigate("/");
  }

	return (
		<header
			style={{
				background: "#fff",
				padding: "10px 20px",
				borderBottom: "1px solid #ddd",
				display: "flex",
				justifyContent: "space-between"
			}}
		>
			<span style={{ fontWeight: "bold", fontSize: "18px" }}>
				{title}
			</span>
			<button onClick={handleLogout}>Logout</button>
		</header>
	);
}