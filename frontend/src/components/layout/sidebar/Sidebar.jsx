import { useEffect, useState } from "react";
import SidebarLink from "./SidebarLink";
import SidebarItem from "./SidebarItem";
import { useAuth } from "../../../hooks/useAuth";

// Icons
import BooksIcon from "../../icons/BooksIcon";
import DashboardIcon from "../../icons/DashboardIcon";
import DefaultIcon from "../../icons/DefaultIcon";
import UsersIcon from "../../icons/UsersIcon";
import RolesIcon from "../../icons/RolesIcon";
import CategoriesIcon from "../../icons/CategoriesIcon";
import CopiesIcon from "../../icons/CopiesIcon";
import PublishersIcon from "../../icons/PublishersIcon";
import LoansIcon from "../../icons/LoansIcon";
import BillIcon from "../../icons/BillIcon";
import ManagementIcon from "../../icons/ManagementIcon";

export default function Sidebar() {
	const { token, user } = useAuth();
	
	// const [collapsed, setCollapsed] = useState(false);
	const [collapsed, setCollapsed] = useState(() => {
		return localStorage.getItem("sidebar-collapsed") === "true";
	});

	const menuItems = [{
			to: "/dashboard",
			label: "Dashboard",
			icon: <DashboardIcon />,
			roles: [
				"SUPER_ADMIN",
				"ADMIN",
				"LIBRARIAN",
				"TEACHER",
				"STUDENT",
				"GUEST"
			],
		},{
			to: "/books",
			label: "Books",
			icon: <BooksIcon />,
			roles: [
			"SUPER_ADMIN",
			"ADMIN",
			"LIBRARIAN",
			"TEACHER",
			"STUDENT",
			"GUEST",
			],
		},{
			to: "/copies",
			label: "Copies",
			icon: <CopiesIcon />,
			roles: ["ADMIN", "SUPER_ADMIN"],
		},{
			to: "/publishers",
			label: "Publishers",
			icon: <PublishersIcon />,
			roles: ["ADMIN", "SUPER_ADMIN"],
		},{
			to: "/categories",
			label: "Categories",
			icon: <CategoriesIcon />,
			roles: ["ADMIN", "SUPER_ADMIN"],
		},{
			to: "/loans",
			label: "Loans",
			icon: <LoansIcon />,
			roles: ["ADMIN", "SUPER_ADMIN"],
		},{
			to: "/fines",
			label: "Fines",
			icon: <BillIcon />,
			roles: ["ADMIN", "SUPER_ADMIN"],
		},{
			to: "/users",
			label: "Users",
			icon: <UsersIcon />,
			roles: ["ADMIN", "SUPER_ADMIN"],
		},{
			to: "/authors",
			label: "Authors",
			icon: <UsersIcon />,
			roles: ["ADMIN", "SUPER_ADMIN"],
		},{
			to: "/management",
			label: "Management",
			icon: <ManagementIcon />,
			roles: ["SUPER_ADMIN"],
		},
	];

	useEffect(() => {
		localStorage.setItem("sidebar-collapsed", collapsed);
	}, [collapsed]);

	return (
		<div
			className="sidebar"
			style={{
				width: collapsed ? "90px" : "250px",
				transition: "width 0.3s",
			}}
		>

			<div>
				{!collapsed &&
					<div className="row row-cols-12 align-items-center">
						<h3 className="col-8">UniBook</h3>
						<button className="btn btn-sm mb-3 col-4" onClick={() => setCollapsed(!collapsed)}>
							<svg width="25px" height="25px" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg"><g id="SVGRepo_bgCarrier" strokeWidth="0"></g><g id="SVGRepo_tracerCarrier" strokeLinecap="round" strokeLinejoin="round"></g><g id="SVGRepo_iconCarrier"> <path d="M4 5V19M20 12H8M8 12L11 15M8 12L11 9" stroke="#ffffff" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round"></path> </g></svg>
						</button>
					</div>
					||
					<button className="btn btn-sm mb-3 col-4" onClick={() => setCollapsed(!collapsed)}>
						<svg width="25px" height="25px" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg"><g id="SVGRepo_bgCarrier" strokeWidth="0"></g><g id="SVGRepo_tracerCarrier" strokeLinecap="round" strokeLinejoin="round"></g><g id="SVGRepo_iconCarrier"> <path d="M20 5V19M4 12L16 12M16 12L13 9M16 12L13 15" stroke="#ffffff" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round"></path> </g></svg>
					</button>
				}
			</div>

			<nav className="d-flex flex-column gap-2">

				{
					user?.roles?.includes("ADMIN") ?
					<div>
						<SidebarItem to="/dashboard" icon={<DashboardIcon />} label="Dashboard" collapsed={collapsed}/>
						<SidebarItem to="/books" icon={<BooksIcon />} label="Books" collapsed={collapsed}/>
						<SidebarItem to="/copies" icon={<CopiesIcon />} label="Copies" collapsed={collapsed}/>
						<SidebarItem to="/publishers" icon={<PublishersIcon />} label="Publishers" collapsed={collapsed}/>
						<SidebarItem to="/categories" icon={<CategoriesIcon />} label="Categories" collapsed={collapsed}/>
						<SidebarItem to="/loans" icon={<LoansIcon />} label="Loans" collapsed={collapsed}/>
						<SidebarItem to="/fines" icon={<BillIcon />} label="Fines" collapsed={collapsed}/>
						<SidebarItem to="/users" icon={<UsersIcon />} label="Users" collapsed={collapsed}/>
						<SidebarItem to="/authors" icon={<UsersIcon />} label="Authors" collapsed={collapsed}/>
						{
							user?.roles?.includes("SUPER_ADMIN") ?
								<SidebarItem to="/management" icon={<ManagementIcon />} label="Management" collapsed={collapsed}/>
							: ""
						}
					</div>
					: user?.roles?.includes("LIBRARIAN") || user?.roles?.includes("TEACHER") || user?.roles?.includes("STUDENT") ? 
						<div>
							<SidebarItem to="/books" icon={<BooksIcon />} label="Books" collapsed={collapsed}/>
							<SidebarItem to="/loans/1" icon={<LoansIcon />} label="My Loans" collapsed={collapsed}/>
							<SidebarItem to="/fines/1" icon={<BillIcon />} label="My Fines" collapsed={collapsed}/>
						</div>
					: user?.roles?.includes("GUEST") ? 
						<div>
							<SidebarItem to="/books" icon={<BooksIcon />} label="Books" collapsed={collapsed}/>
						</div>
					: ""
				}

			</nav>
		</div>
	);
}