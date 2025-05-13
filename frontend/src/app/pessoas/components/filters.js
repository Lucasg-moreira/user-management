import { useState } from "react";

export const Filters = ({ onFilter }) => {
  const [filters, setFilters] = useState({
    createdAt: "",
    name: "",
    cpfCnpj: "",
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFilters(prev => ({
      ...prev,
      [name]: value
    }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    onFilter(filters);
  };

  return (
    <div className="bg-white dark:bg-gray-800 p-6 rounded-lg shadow-lg mb-6">
      <h2 className="text-lg font-semibold text-gray-900 dark:text-white mb-4">Filtros</h2>
      <form className="grid grid-cols-1 md:grid-cols-3 gap-4">
        <div>
          <label htmlFor="birthDate" className="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1">
            Data de Cadastro
          </label>
          <input
            type="date"
            id="createdAt"
            name="createdAt"
            value={filters.birthDate}
            onChange={handleChange}
            className="w-full px-4 py-2 border border-gray-300 dark:border-gray-600 rounded-lg focus:ring-2 focus:ring-indigo-500 focus:border-transparent dark:bg-gray-700 dark:text-white"
          />
        </div>
        <div>
          <label htmlFor="email" className="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1">
            Nome / Razão Social
          </label>
          <input
            type="name"
            id="name"
            name="name"
            value={filters.name}
            onChange={handleChange}
            className="w-full px-4 py-2 border border-gray-300 dark:border-gray-600 rounded-lg focus:ring-2 focus:ring-indigo-500 focus:border-transparent dark:bg-gray-700 dark:text-white"
            placeholder="Buscar por nome/razão social"
          />
        </div>
        <div>
          <label htmlFor="cpfCnpj" className="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1">
            CPF/CNPJ
          </label>
          <input
            type="text"
            id="cpfCnpj"
            name="cpfCnpj"
            value={filters.cpfCnpj}
            onChange={handleChange}
            className="w-full px-4 py-2 border border-gray-300 dark:border-gray-600 rounded-lg focus:ring-2 focus:ring-indigo-500 focus:border-transparent dark:bg-gray-700 dark:text-white"
            placeholder="Buscar por CPF/CNPJ"
          />
        </div>

        <div className="md:col-span-5 flex justify-end">
          <button
            onClick={handleSubmit}
            type="button"
            className="px-4 py-2 bg-indigo-600 text-white rounded-lg hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:ring-offset-2 transition-colors duration-200"
          >
            Filtrar
          </button>
        </div>
      </form>
    </div>
  );
};