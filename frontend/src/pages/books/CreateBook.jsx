import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import AppLayout from "../../components/layout/AppLayout";
import { createBook } from "../../services/books";
import { getAuthors } from "../../services/authors";
import { getCategories } from "../../services/categories";
import { getPublishers } from "../../services/publishers";

import FormInput from "../../components/forms/FormInput";
import CheckboxGroup from "../../components/forms/CheckboxGroup";
import FormActions from "../../components/forms/FormActions";
import FormSelect from "../../components/forms/FormSelect";

const initialForm = {
    title: "",
    isbn: "",
    publicationYear: "",
    description: "",
    publisherId: "",
    authorIds: [],
    categoryIds: [],
};

export default function CreateBook({ title }) {
  const navigate = useNavigate();

  const [generalError, setGeneralError] = useState("");
  const [errors, setErrors] = useState({});
  const [saving, setSaving] = useState(false);

  const [form, setForm] = useState(initialForm);
  const [authors, setAuthors] = useState([]);
  const [loadingAuthors, setLoadingAuthors] = useState(true);
  const [categories, setCategories] = useState([]);
  const [loadingCategories, setLoadingCategories] = useState(true);
  const [publishers, setPublishers] = useState([]);
  const [loadingPublishers, setLoadingPublishers] = useState(true);
  
  useEffect(() => {

    // Load Form options (authors, categories, publishers)
    async function loadOptions() {
      try{
        const data = await getAuthors();
        setAuthors(data);
      }catch {
        setGeneralError("Unable to load authors.");
      } finally{
        setLoadingAuthors(false);
      }

      try{
        const data = await getCategories();
        setCategories(data);
      }catch {
        setGeneralError("Unable to load categories.");
      } finally{
        setLoadingCategories(false);
      }

      try{
        const data = await getPublishers();
        setPublishers(data);
      }catch {
        setGeneralError("Unable to load publishers.");
      } finally{
        setLoadingPublishers(false);
      }
    }

    loadOptions();
  }, []);

  function handleChange(e) {
    const { name, value } = e.target;

    setForm(prev => ({
      ...prev,
      [name]: value,
    }));

    clearFieldError(name);
  }

  function clearFieldError(field) {
    setErrors(previous => ({
      ...previous,
      [field]: "",
    }));
  }

  async function handleSubmit(e) {
    e.preventDefault();

    setSaving(true);
    setErrors({});

    try {
      await createBook(form);

      navigate("/books", {
        state: {
          success: "Book created successfully.",
        },
      });
    } catch (err) {
      if (error && typeof error === "object") {
        setErrors(error);
      } else {
        setGeneralError("Unable to create book.");
      }
    } finally {
      setSaving(false);
    }
  }

  return (
    <AppLayout title={title}>
      <div className="container">
        <h1>Create Book</h1>

        <form onSubmit={handleSubmit} className="card p-4">
          <FormInput label="Title" name="title" value={form.title} onChange={handleChange} error={errors.title} required/>
          <FormInput label="ISBN" name="isbn" value={form.isbn} onChange={handleChange} error={errors.isbn} required/>
          <FormInput label="Publication Year" name="publicationYear" type="number" value={form.publicationYear} onChange={handleChange} error={errors.publicationYear} required/>
          <FormInput label="Description" name="description" value={form.description} onChange={handleChange} error={errors.description} required/>
          <FormSelect label="Publisher" name="publisherId" value={form.publisherId} disabled={loadingPublishers} onChange={handleChange} options={publishers} error={errors.publisherId} placeholder={loadingPublishers ? "Loading publishers..." : "Select a publisher"} getOptionLabel={publisher => publisher.title} />
          <FormSelect isMulti label="Authors" name="authorIds" options={authors} disabled={loadingAuthors} value={form.authorIds} onChange={handleChange} error={errors.authorIds} placeholder={loadingAuthors ? "Loading authors..." : "Search and select authors"} getOptionLabel={author => author.person.name} getOptionValue={author => author.id}/>
          <FormSelect isMulti label="Categories" name="categoryIds" options={categories} disabled={loadingCategories} value={form.categoryIds} onChange={handleChange} error={errors.categoryIds} placeholder={loadingCategories ? "Loading categories..." : "Search and select categories"} getOptionLabel={category => category.title} getOptionValue={category => category.id} />
          <button type="submit" className="btn btn-primary" disabled={saving}>
            {saving ? "Saving..." : "Create Book"}
          </button>
        </form>
      </div>
    </AppLayout>
  );
}