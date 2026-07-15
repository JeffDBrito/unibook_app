import Select from "react-select";

export default function FormSelect({
    label,
    name,
    options = [],
    value,
    onChange,
    error,
    isMulti = false,
    placeholder = "Select...",
    disabled = false,
    getOptionLabel = option => option.title,
    getOptionValue = option => option.id,
}) {

    const formattedOptions = options.map(option => ({
        value: getOptionValue(option),
        label: getOptionLabel(option),
    }));

    const selectedOption = isMulti
        ? formattedOptions.filter(option => value?.includes(option.value))
        : formattedOptions.find(option => option.value === value) || null;

    function handleChange(selected) {
        if (isMulti) {
            onChange({
                target: {
                    name,
                    value: selected
                        ? selected.map(option => option.value)
                        : [],
                },
            });
        } else {
            onChange({
                target: {
                    name,
                    value: selected ? selected.value : "",
                },
            });
        }
    }

    return (
        <div className="mb-3">
            <label className="form-label">
                {label}
            </label>

            <Select
                isMulti={isMulti}
                isSearchable
                isDisabled={disabled}
                options={formattedOptions}
                value={selectedOption}
                onChange={handleChange}
                placeholder={placeholder}
                classNamePrefix="react-select"
            />

            {error && (
                <div className="text-danger mt-1">
                    {error}
                </div>
            )}
        </div>
    );
}