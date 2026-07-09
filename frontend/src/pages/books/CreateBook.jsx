import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import AppLayout from "../../components/layout/AppLayout";
import { createBook } from "../../services/books";
import { getAuthors } from "../../services/authors";
import { getCategories } from "../../services/categories";
import { getPublishers } from "../../services/publishers";

export default function CreateBook({ title }) {
  const navigate = useNavigate();

  const [form, setForm] = useState({
    title: "",
    isbn: "",
    publicationYear: "",
    description: "",
    publisherId: "",
    authorIds: [],
    categoryIds: [],
  });

  const [authors, setAuthors] = useState([]);
  const [categories, setCategories] = useState([]);
  const [publishers, setPublishers] = useState([]);
  const [errors, setErrors] = useState({});
  const [saving, setSaving] = useState(false);

  useEffect(() => {
    async function loadOptions() {
      setAuthors(await getAuthors());
      setCategories(await getCategories());
      setPublishers(await getPublishers());
    }

    loadOptions();
  }, []);

  function handleChange(e) {
    const { name, value } = e.target;

    setForm(prev => ({
      ...prev,
      [name]: value,
    }));

    setErrors(prev => ({
      ...prev,
      [name]: "",
    }));
  }

  function handleCheckboxChange(e, field) {
    const id = Number(e.target.value);

    setForm(prev => ({
      ...prev,
      [field]: e.target.checked
        ? [...prev[field], id]
        : prev[field].filter(item => item !== id),
    }));

    setErrors(prev => ({
      ...prev,
      [field]: "",
    }));
  }

  async function handleSubmit(e) {
    e.preventDefault();

    setSaving(true);
    setErrors({});

    try {
      await createBook({
        ...form,
        publicationYear: Number(form.publicationYear),
        publisherId: Number(form.publisherId),
      });

      navigate("/books");
    } catch (err) {
      setErrors(err);
    } finally {
      setSaving(false);
    }
  }

  return (
    <AppLayout title={title}>
      <div className="container">
        <h1>Create Book</h1>

        <form onSubmit={handleSubmit} className="card p-4">
          <div className="mb-3">
            <label className="form-label">Title</label>
            <input
              name="title"
              className={`form-control ${errors.title ? "is-invalid" : ""}`}
              value={form.title}
              onChange={handleChange}
            />
            {errors.title && <div className="invalid-feedback">{errors.title}</div>}
          </div>

          <div className="mb-3">
            <label className="form-label">ISBN</label>
            <input
              name="isbn"
              className={`form-control ${errors.isbn ? "is-invalid" : ""}`}
              value={form.isbn}
              onChange={handleChange}
            />
            {errors.isbn && <div className="invalid-feedback">{errors.isbn}</div>}
          </div>

          <div className="mb-3">
            <label className="form-label">Publication Year</label>
            <input
              name="publicationYear"
              type="number"
              className={`form-control ${errors.publicationYear ? "is-invalid" : ""}`}
              value={form.publicationYear}
              onChange={handleChange}
            />
            {errors.publicationYear && (
              <div className="invalid-feedback">{errors.publicationYear}</div>
            )}
          </div>

          <div className="mb-3">
            <label className="form-label">Description</label>
            <textarea
              name="description"
              className={`form-control ${errors.description ? "is-invalid" : ""}`}
              value={form.description}
              onChange={handleChange}
              rows="3"
            />
            {errors.description && (
              <div className="invalid-feedback">{errors.description}</div>
            )}
          </div>

          <div className="mb-3">
            <label className="form-label">Publisher</label>
            <select
              name="publisherId"
              className={`form-select ${errors.publisherId ? "is-invalid" : ""}`}
              value={form.publisherId}
              onChange={handleChange}
            >
              <option value="">Select a publisher</option>
              {publishers.map(publisher => (
                <option key={publisher.id} value={publisher.id}>
                  {publisher.title}
                </option>
              ))}
            </select>
            {errors.publisherId && (
              <div className="invalid-feedback">{errors.publisherId}</div>
            )}
          </div>

          <div className="mb-3">
            <label className="form-label">Authors</label>

            {authors.map(author => (
              <div className="form-check" key={author.id}>
                <input
                  className="form-check-input"
                  type="checkbox"
                  value={author.id}
                  checked={form.authorIds.includes(author.id)}
                  onChange={(e) => handleCheckboxChange(e, "authorIds")}
                />
                <label className="form-check-label">
                  {author.person.name}
                </label>
              </div>
            ))}

            {errors.authorIds && (
              <div className="text-danger mt-1">{errors.authorIds}</div>
            )}
          </div>

          <div className="mb-4">
            <label className="form-label">Categories</label>

            {categories.map(category => (
              <div className="form-check" key={category.id}>
                <input
                  className="form-check-input"
                  type="checkbox"
                  value={category.id}
                  checked={form.categoryIds.includes(category.id)}
                  onChange={(e) => handleCheckboxChange(e, "categoryIds")}
                />
                <label className="form-check-label">
                  {category.title}
                </label>
              </div>
            ))}

            {errors.categoryIds && (
              <div className="text-danger mt-1">{errors.categoryIds}</div>
            )}
          </div>

          <button type="submit" className="btn btn-primary" disabled={saving}>
            {saving ? "Saving..." : "Create Book"}
          </button>
        </form>
      </div>
    </AppLayout>
  );
}