import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { getUser, updateUser } from "../../services/users";

import AppLayout from "../../components/layout/AppLayout";
import FormInput from "../../components/forms/FormInput";
import CheckboxGroup from "../../components/forms/CheckboxGroup";
import FormActions from "../../components/forms/FormActions";
import FormSelect from "../../components/forms/FormSelect";

import { getRoles } from "../../services/roles";

const initialForm = {
  name: "",
  email: "",
  birthDate: "",
  login: "",
  password: "",
  roleIds: [],
};

export default function EditUser({ title }) {
  const { id } = useParams();
  const navigate = useNavigate();

  const [form, setForm] = useState(initialForm);
  const [roles, setRoles] = useState([]);
  const [errors, setErrors] = useState({});
  const [saving, setSaving] = useState(false);
  const [loadingRoles, setLoadingRoles] = useState(true);
  const [loadingUser, setLoadingUser] = useState(true);
  const [generalError, setGeneralError] = useState("");

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

  useEffect(() => {
    async function loadUser() {
      try {
        const user = await getUser(id);

        setForm({
          name: user.person.name || "",
          email: user.person.email || "",
          birthDate: user.person.birthDate || "",
          login: user.login || "",
          password: "",
          roleIds: user.roleIds || [],
        });
      } finally {
        setLoadingUser(false);
      }
    }

    loadUser();

  }, [id]);

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

  function handleRoleChange(e) {
    const roleId = Number(e.target.value);

    setForm(prev => ({
      ...prev,
      roleIds: e.target.checked
        ? [...prev.roleIds, roleId]
        : prev.roleIds.filter(id => id !== roleId),
    }));

    setErrors(prev => ({
      ...prev,
      roleIds: "",
    }));
  }

  async function handleSubmit(e) {
    e.preventDefault();

    setSaving(true);
    setErrors({});

    const payload = { ...form };

    if (!payload.password) {
      delete payload.password;
    }

    try {
      await updateUser(id, payload);
      navigate("/users");
    } catch (err) {
      setErrors(err);
    } finally {
      setSaving(false);
    }
  }

  return (
    <AppLayout title={title}>
      <div className="container">
        {
          loadingUser ?
            <span>Loading user details...</span> :
            <div>
              <h1>Edit User</h1>

              <form onSubmit={handleSubmit} className="card p-4">
                <FormInput label="Name" name="name" value={form.name} onChange={handleChange} error={errors.name} />
                <FormInput label="Email" name="email" type="email" value={form.email} onChange={handleChange} error={errors.email} />
                <FormInput label="Birth date" name="birthDate" type="date" value={form.birthDate} onChange={handleChange} error={errors.birthDate} />
                <FormInput label="Login" name="login" value={form.login} onChange={handleChange} error={errors.login} />
                <FormInput label="Password" name="password" type="password" value={form.password} onChange={handleChange} error={errors.password} />

                {loadingRoles ? (
                  <p className="text-muted">Loading roles...</p>
                ) : (
                  <FormSelect label="Roles" name="roleIds" value={form.roleIds} onChange={handleChange} options={roles} error={errors.roleIds} isMulti placeholder="Select roles..." getOptionLabel={role => role.name} getOptionValue={role => role.id} />
                )}

                <FormActions
                  saving={saving}
                  submitLabel="Update User"
                  savingLabel="Updating..."
                  onCancel={() => navigate("/users")}
                />
              </form>
            </div>
        }
      </div>
    </AppLayout>
  );
}