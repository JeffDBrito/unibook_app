import { useAuth } from "../../hooks/useAuth";
import { useNavigate } from "react-router-dom";

export default function Header({ title }) {
	const { logout } = useAuth();
	const navigate = useNavigate();
	const { token, user } = useAuth();

  function handleLogout() {
	console.log("Logging out user:", user);
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
			<div>
				<span style={{ fontWeight: "bold", fontSize: "18px" }}>
					{title}
				</span>
			</div>
			<div>
			<span className="mx-3"> {user.name}</span>
			<button onClick={handleLogout}>Logout</button>
			</div>
		</header>
	);
}