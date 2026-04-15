export default function Input({
  type = "text",
  placeholder,
  value,
  onChange,
  ...props
}) {
  return (
    <input
      type={type}
      placeholder={placeholder}
      value={value}
      onChange={onChange}
      style={{
        width: "100%",
        padding: "10px",
        marginBottom: "10px",
        borderRadius: "6px",
        border: "1px solid #ccc"
      }}
      {...props}
    />
  );
}