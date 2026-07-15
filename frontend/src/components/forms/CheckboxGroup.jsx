export default function CheckboxGroup({
  label,
  name,
  options,
  selectedValues,
  onChange,
  error,
  getOptionLabel = option => option.name,
  getOptionValue = option => option.id,
}) {
  return (
    <div className="mb-4">
      <label className="form-label d-block">
        {label}
      </label>

      {options.map(option => {
        const value = getOptionValue(option);

        return (
          <div className="form-check" key={value}>
            <input
              id={`${name}-${value}`}
              name={name}
              className="form-check-input"
              type="checkbox"
              value={value}
              checked={selectedValues.includes(value)}
              onChange={onChange}
            />

            <label
              className="form-check-label"
              htmlFor={`${name}-${value}`}
            >
              {getOptionLabel(option)}
            </label>
          </div>
        );
      })}

      {error && (
        <div className="text-danger small mt-1">
          {error}
        </div>
      )}
    </div>
  );
}