export default function FormActions({
  saving,
  submitLabel = "Save",
  savingLabel = "Saving...",
  onCancel,
}) {
  return (
    <div className="d-flex gap-2">
      <button
        type="submit"
        className="btn btn-primary"
        disabled={saving}
      >
        {saving ? savingLabel : submitLabel}
      </button>

      {onCancel && (
        <button
          type="button"
          className="btn btn-outline-secondary"
          onClick={onCancel}
          disabled={saving}
        >
          Cancel
        </button>
      )}
    </div>
  );
}