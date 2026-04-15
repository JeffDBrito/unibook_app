export default function Button({ children, onClick, type = "button" }) {
  return (
    <button
      type={type}
      onClick={onClick}
      style={{
        width: "100%",
        padding: "10px",
        borderRadius: "6px",
        border: "none",
        backgroundColor: "#4f46e5",
        color: "#fff",
        cursor: "pointer"
      }}
    >
      {children}
    </button>
  );
}