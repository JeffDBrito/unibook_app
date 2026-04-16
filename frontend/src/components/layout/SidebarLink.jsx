import { Link, useLocation } from "react-router-dom";

export default function SidebarLink({ to, children }) {
	const location = useLocation();
	const isActive = location.pathname === to;

	return (
		<Link
			className="sidebar-link"
			to={to}
			style={{
				...baseStyle,
				...(isActive ? activeStyle : {})
			}}
			onMouseEnter={(e) => {
				if (!isActive) e.target.style.background = hoverColor;
				if (!isActive) e.target.style.fontSize = "17px";
			}}
			onMouseLeave={(e) => {
				if (!isActive) e.target.style.background = "transparent";
				if (!isActive) e.target.style.fontSize = "16px";
			}}
		>
			{children}
		</Link>
	);
}

const hoverColor = "#374151";

const baseStyle = {
	color: "#fff",
	textDecoration: "none",
	padding: "8px",
	borderRadius: "4px",
	transition: "0.2s"
};

const activeStyle = {
	background: "#374151"
};