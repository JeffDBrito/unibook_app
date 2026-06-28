import { Link, useLocation } from "react-router-dom";

export default function SidebarLink({ to, children }) {
	const location = useLocation();
	const isActive = location.pathname === to;

	return (
		<Link
			className="sidebar-link"
			to={to}
		>
			{children}
		</Link>
	);
}
