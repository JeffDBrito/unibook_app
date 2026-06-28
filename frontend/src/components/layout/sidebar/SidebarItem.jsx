import SidebarLink from "./SidebarLink";

export default function SidebarItem({
  to,
  icon,
  label,
  collapsed,
  size = "sm"
}) {
  return (
    <SidebarLink to={to}>
      <div
        className={`d-flex align-items-center ${
          collapsed ? "justify-content-center" : ""
        }`}
      >
        <span className={`sidebar-icon icon-${size}`}>
          {icon}
        </span>

        {!collapsed && (
          <span className="ms-3">
            {label}
          </span>
        )}
      </div>
    </SidebarLink>
  );
}