import { useEffect, useState } from "react";
import SidebarLink from "./SidebarLink";
import SidebarItem from "./SidebarItem";

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

export default function Sidebar() {
// const [collapsed, setCollapsed] = useState(false);
const [collapsed, setCollapsed] = useState(() => {
	return localStorage.getItem("sidebar-collapsed") === "true";
});

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
					<svg width="25px" height="25px" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg"><g id="SVGRepo_bgCarrier" stroke-width="0"></g><g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round"></g><g id="SVGRepo_iconCarrier"> <path d="M4 5V19M20 12H8M8 12L11 15M8 12L11 9" stroke="#ffffff" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"></path> </g></svg>
				</button>
			</div> 
			|| 
			<button className="btn btn-sm mb-3 col-4" onClick={() => setCollapsed(!collapsed)}>
				<svg width="25px" height="25px" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg"><g id="SVGRepo_bgCarrier" stroke-width="0"></g><g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round"></g><g id="SVGRepo_iconCarrier"> <path d="M20 5V19M4 12L16 12M16 12L13 9M16 12L13 15" stroke="#ffffff" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"></path> </g></svg>
			</button>
			}
		</div>

      	<nav className="d-flex flex-column gap-2">

			<SidebarItem
				to="/dashboard"
				icon={<DashboardIcon/>}
				label="Dashboard"
				collapsed={collapsed}
			/>
			
			<SidebarItem
				to="/books"
				icon={<BooksIcon/>}
				label="Books"
				collapsed={collapsed}
			/>

			<SidebarItem
				to="/copies"
				icon={<CopiesIcon/>}
				label="Copies"
				collapsed={collapsed}
			/>

			<SidebarItem
				to="/publishers"
				icon={<PublishersIcon/>}
				label="Publishers"
				collapsed={collapsed}
			/>

			<SidebarItem
				to="/categories"
				icon={<CategoriesIcon/>}
				label="Categories"
				collapsed={collapsed}
			/>

			<SidebarItem
				to="/loans"
				icon={<LoansIcon/>}
				label="Loans"
				collapsed={collapsed}
			/>

			<SidebarItem
				to="/fines"
				icon={<BillIcon/>}
				label="Fines"
				collapsed={collapsed}
			/>

			<SidebarItem
				to="/users"
				icon={<UsersIcon/>}
				label="Users"
				collapsed={collapsed}
			/>

			<SidebarItem
				to="/authors"
				icon={<UsersIcon/>}
				label="Authors"
				collapsed={collapsed}
			/>

			<SidebarItem
				to="/roles"
				icon={<RolesIcon/>}
				label="Roles"
				collapsed={collapsed}
			/>


		</nav>
    </div>
  );
}