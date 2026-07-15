import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { toast } from "react-toastify";

import AppLayout from "../../components/layout/AppLayout";
import FormInput from "../../components/forms/FormInput";
import CheckboxGroup from "../../components/forms/CheckboxGroup";
import FormActions from "../../components/forms/FormActions";
import FormSelect from "../../components/forms/FormSelect";

import { createUser } from "../../services/users";
import { getRoles } from "../../services/roles";

const initialForm = {
  name: "",
  email: "",
  birthDate: "",
  login: "",
  password: "",
  roleIds: [],
};

export default function CreateUser({ title }) {
  const navigate = useNavigate();

  const [generalError, setGeneralError] = useState("");
  const [errors, setErrors] = useState({});
  const [saving, setSaving] = useState(false);

  const [form, setForm] = useState(initialForm);
  const [roles, setRoles] = useState([]);
  const [loadingRoles, setLoadingRoles] = useState(true);

  useEffect(() => {
    async function loadRoles() {
      try {
        const data = await getRoles();
        setRoles(data);
      } catch {
        setGeneralError("Unable to load roles.");
      } finally {
        setLoadingRoles(false);
      }
    }

    loadRoles();
  }, []);

  function handleChange(event) {
    const { name, value } = event.target;

    setForm(previous => ({
      ...previous,
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

  async function handleSubmit(event) {
    event.preventDefault();

    setSaving(true);
    setErrors({});
    setGeneralError("");

    try {
      await createUser(form);

      navigate("/users", {
        state: {
          success: "User created successfully.",
        },
      });

      toast.success("User created successfully.");
    } catch (error) {
      if (error && typeof error === "object") {
        toast.error("Please fix the errors in the form.");
        setErrors(error);
      } else {
        setGeneralError("Unable to create user.");
        toast.error("Unable to create user.");
      }
    } finally {
      setSaving(false);
    }
  }

  return (
    <AppLayout title={title}>
      <div className="container">
        <h1 className="mb-4">Create User</h1>

        {generalError && (
          <div className="alert alert-danger">
            {generalError}
          </div>
        )}

        <form onSubmit={handleSubmit} className="card p-4">
          <FormInput label="Name" name="name" value={form.name} onChange={handleChange} error={errors.name} required />
          <FormInput label="Email" name="email" type="email" value={form.email} onChange={handleChange} error={errors.email} required />
          <FormInput label="Birth date" name="birthDate" type="date" value={form.birthDate} onChange={handleChange} error={errors.birthDate} required />
          <FormInput label="Login" name="login" value={form.login} onChange={handleChange} error={errors.login} required />
          <FormInput label="Password" name="password" type="password" value={form.password} onChange={handleChange} error={errors.password} required />

          {loadingRoles ? (
            <p className="text-muted">Loading roles...</p>
          ) : (
            <FormSelect label="Roles" name="roleIds" value={form.roleIds} onChange={handleChange} options={roles} error={errors.roleIds} isMulti placeholder="Select roles..." getOptionLabel={role => role.name} getOptionValue={role => role.id}
            />
          )}

          <FormActions saving={saving} submitLabel="Create User" savingLabel="Creating..." onCancel={() => navigate("/users")} />
        </form>
      </div>
    </AppLayout>
  );
}