export function CompanyPersonForm({ formData, handleChange, isLoading, getFieldError }) {
  return (
    <>
      <div>
        <label htmlFor="name" className="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1">
          Nome Fantasia *
        </label>
        <input
          type="text"
          id="name"
          name="name"
          value={formData.name}
          onChange={handleChange}
          disabled={isLoading}
          className={`w-full px-4 py-2 border rounded-lg focus:ring-2 focus:ring-indigo-500 focus:border-transparent dark:bg-gray-700 dark:text-white disabled:opacity-50 ${
            getFieldError('name')
              ? 'border-red-300 dark:border-red-600'
              : 'border-gray-300 dark:border-gray-600'
          }`}
          placeholder="Digite o nome fantasia"
        />
        {getFieldError('name') && (
          <p className="mt-1 text-sm text-red-600 dark:text-red-400">{getFieldError('name')}</p>
        )}
      </div>

      <div>
        <label htmlFor="telephone" className="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1">
          Telefone *
        </label>
        <input
          type="tel"
          id="telephone"
          name="telephone"
          value={formData.telephone}
          onChange={handleChange}
          disabled={isLoading}
          className={`w-full px-4 py-2 border rounded-lg focus:ring-2 focus:ring-indigo-500 focus:border-transparent dark:bg-gray-700 dark:text-white disabled:opacity-50 ${
            getFieldError('telephone')
              ? 'border-red-300 dark:border-red-600'
              : 'border-gray-300 dark:border-gray-600'
          }`}
          placeholder="(00) 00000-0000"
        />
        {getFieldError('telephone') && (
          <p className="mt-1 text-sm text-red-600 dark:text-red-400">{getFieldError('telephone')}</p>
        )}
      </div>

      <div>
        <label htmlFor="companyName" className="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1">
          Razão Social *
        </label>
        <input
          type="text"
          id="companyName"
          name="companyName"
          value={formData.companyName}
          onChange={handleChange}
          disabled={isLoading}
          className={`w-full px-4 py-2 border rounded-lg focus:ring-2 focus:ring-indigo-500 focus:border-transparent dark:bg-gray-700 dark:text-white disabled:opacity-50 ${
            getFieldError('companyName')
              ? 'border-red-300 dark:border-red-600'
              : 'border-gray-300 dark:border-gray-600'
          }`}
          placeholder="Digite a razão social"
        />
        {getFieldError('companyName') && (
          <p className="mt-1 text-sm text-red-600 dark:text-red-400">{getFieldError('companyName')}</p>
        )}
      </div>

      <div>
        <label htmlFor="fantasyName" className="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1">
          Nome Fantasia *
        </label>
        <input
          type="text"
          id="fantasyName"
          name="fantasyName"
          value={formData.fantasyName}
          onChange={handleChange}
          disabled={isLoading}
          className={`w-full px-4 py-2 border rounded-lg focus:ring-2 focus:ring-indigo-500 focus:border-transparent dark:bg-gray-700 dark:text-white disabled:opacity-50 ${
            getFieldError('fantasyName')
              ? 'border-red-300 dark:border-red-600'
              : 'border-gray-300 dark:border-gray-600'
          }`}
          placeholder="Digite o nome fantasia"
        />
        {getFieldError('fantasyName') && (
          <p className="mt-1 text-sm text-red-600 dark:text-red-400">{getFieldError('fantasyName')}</p>
        )}
      </div>

      <div>
        <label htmlFor="cnpj" className="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1">
          CNPJ *
        </label>
        <input
          type="text"
          id="cnpj"
          name="cnpj"
          value={formData.cnpj}
          onChange={handleChange}
          disabled={isLoading}
          className={`w-full px-4 py-2 border rounded-lg focus:ring-2 focus:ring-indigo-500 focus:border-transparent dark:bg-gray-700 dark:text-white disabled:opacity-50 ${
            getFieldError('cnpj')
              ? 'border-red-300 dark:border-red-600'
              : 'border-gray-300 dark:border-gray-600'
          }`}
          placeholder="00.000.000/0000-00"
        />
        {getFieldError('cnpj') && (
          <p className="mt-1 text-sm text-red-600 dark:text-red-400">{getFieldError('cnpj')}</p>
        )}
      </div>
    </>
  );
} 