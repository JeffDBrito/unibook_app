import { useEffect, useState } from "react";
import SidebarItem from "./SidebarItem";
import { useAuth } from "../../../hooks/useAuth";

// Icons
import BooksIcon from "../../icons/BooksIcon";
import DashboardIcon from "../../icons/DashboardIcon";
import UsersIcon from "../../icons/UsersIcon";
import CategoriesIcon from "../../icons/CategoriesIcon";
import CopiesIcon from "../../icons/CopiesIcon";
import PublishersIcon from "../../icons/PublishersIcon";
import LoansIcon from "../../icons/LoansIcon";
import BillIcon from "../../icons/BillIcon";
import ManagementIcon from "../../icons/ManagementIcon";

export default function Sidebar() {
  const { user } = useAuth();

  const [collapsed, setCollapsed] = useState(() => {
    return localStorage.getItem("sidebar-collapsed") === "true";
  });

  const menuItems = [
    {
      to: "/home",
      label: "Home",
      icon: <DashboardIcon />,
      roles: [
        "SUPER_ADMIN",
        "ADMIN",
        "LIBRARIAN",
        "TEACHER",
        "STUDENT",
        "GUEST",
      ],
    },
    {
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
    },
    {
      to: "/copies",
      label: "Copies",
      icon: <CopiesIcon />,
      roles: ["SUPER_ADMIN", "ADMIN", "LIBRARIAN"],
    },
    {
      to: "/publishers",
      label: "Publishers",
      icon: <PublishersIcon />,
      roles: ["SUPER_ADMIN", "ADMIN", "LIBRARIAN"],
    },
    {
      to: "/categories",
      label: "Categories",
      icon: <CategoriesIcon />,
      roles: ["SUPER_ADMIN", "ADMIN", "LIBRARIAN"],
    },
    {
      to: "/loans",
      label: "Loans",
      icon: <LoansIcon />,
      roles: ["SUPER_ADMIN", "ADMIN", "LIBRARIAN"],
    },
    {
      to: "/loan-requests",
      label: "Loan Requests",
      icon: <LoansIcon />,
      roles: ["SUPER_ADMIN", "ADMIN", "LIBRARIAN"],
    },
    {
      to: "/fines",
      label: "Fines",
      icon: <BillIcon />,
      roles: ["SUPER_ADMIN", "ADMIN", "LIBRARIAN"],
    },
    {
      to: "/users",
      label: "Users",
      icon: <UsersIcon />,
      roles: ["SUPER_ADMIN", "ADMIN"],
    },
    {
      to: "/authors",
      label: "Authors",
      icon: <UsersIcon />,
      roles: ["SUPER_ADMIN", "ADMIN", "LIBRARIAN"],
    },
    {
      to: "/management",
      label: "Management",
      icon: <ManagementIcon />,
      roles: ["SUPER_ADMIN"],
    },
    {
      to: "/loans/me",
      label: "My Loans",
      icon: <LoansIcon />,
      roles: ["LIBRARIAN", "TEACHER", "STUDENT"],
    },
    {
      to: "/fines/me",
      label: "My Fines",
      icon: <BillIcon />,
      roles: ["LIBRARIAN", "TEACHER", "STUDENT"],
    },
  ];

  useEffect(() => {
    localStorage.setItem("sidebar-collapsed", collapsed);
  }, [collapsed]);

  const userRoles = user?.roles ?? [];

  const visibleMenuItems = menuItems.filter((menuItem) =>
    menuItem.roles.some((role) => userRoles.includes(role))
  );

  return (
    <div
      className="sidebar"
      style={{
        width: collapsed ? "90px" : "250px",
        transition: "width 0.3s",
      }}
    >
      <div>
        {!collapsed ? (
          <div className="row row-cols-12 align-items-center">
            <h3 className="col-8">UniBook</h3>

            <button
              type="button"
              className="btn btn-sm mb-3 col-4"
              onClick={() => setCollapsed(true)}
              aria-label="Collapse sidebar"
            >
              <svg
                width="25px"
                height="25px"
                viewBox="0 0 24 24"
                fill="none"
                xmlns="http://www.w3.org/2000/svg"
              >
                <path
                  d="M4 5V19M20 12H8M8 12L11 15M8 12L11 9"
                  stroke="#ffffff"
                  strokeWidth="2"
                  strokeLinecap="round"
                  strokeLinejoin="round"
                />
              </svg>
            </button>
          </div>
        ) : (
          <button
            type="button"
            className="btn btn-sm mb-3"
            onClick={() => setCollapsed(false)}
            aria-label="Expand sidebar"
          >
            <svg
              width="25px"
              height="25px"
              viewBox="0 0 24 24"
              fill="none"
              xmlns="http://www.w3.org/2000/svg"
            >
              <path
                d="M20 5V19M4 12L16 12M16 12L13 9M16 12L13 15"
                stroke="#ffffff"
                strokeWidth="2"
                strokeLinecap="round"
                strokeLinejoin="round"
              />
            </svg>
          </button>
        )}
      </div>

      <nav className="d-flex flex-column gap-2">
        {visibleMenuItems.map((menuItem) => (
          <SidebarItem
            key={menuItem.to}
            to={menuItem.to}
            icon={menuItem.icon}
            label={menuItem.label}
            collapsed={collapsed}
          />
        ))}
      </nav>
    </div>
  );
}