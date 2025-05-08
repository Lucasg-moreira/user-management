"use client";

import { useState, useCallback } from "react";
import { useRouter } from "next/navigation";

const validateCPF = (cpf) => {
  cpf = cpf.replace(/[^\d]/g, '');

  if (cpf.length !== 11) return false;

  if (/^(\d)\1{10}$/.test(cpf)) return false;

  let sum = 0;
  for (let i = 0; i < 9; i++) {
    sum += parseInt(cpf.charAt(i)) * (10 - i);
  }
  let remainder = 11 - (sum % 11);
  let firstDigit = remainder > 9 ? 0 : remainder;
  if (firstDigit !== parseInt(cpf.charAt(9))) return false;

  sum = 0;
  for (let i = 0; i < 10; i++) {
    sum += parseInt(cpf.charAt(i)) * (11 - i);
  }
  remainder = 11 - (sum % 11);
  let secondDigit = remainder > 9 ? 0 : remainder;
  if (secondDigit !== parseInt(cpf.charAt(10))) return false;

  return true;
};

const validateCNPJ = (cnpj) => {
  cnpj = cnpj.replace(/[^\d]/g, '');

  if (cnpj.length !== 14) return false;
  if (/^(\d)\1{13}$/.test(cnpj)) return false;

  let size = cnpj.length - 2;
  let numbers = cnpj.substring(0, size);
  let digits = cnpj.substring(size);
  let sum = 0;
  let pos = size - 7;

  for (let i = size; i >= 1; i--) {
    sum += numbers.charAt(size - i) * pos--;
    if (pos < 2) pos = 9;
  }

  let result = sum % 11 < 2 ? 0 : 11 - (sum % 11);
  if (result !== parseInt(digits.charAt(0))) return false;

  size = size + 1;
  numbers = cnpj.substring(0, size);
  sum = 0;
  pos = size - 7;

  for (let i = size; i >= 1; i--) {
    sum += numbers.charAt(size - i) * pos--;
    if (pos < 2) pos = 9;
  }

  result = sum % 11 < 2 ? 0 : 11 - (sum % 11);
  if (result !== parseInt(digits.charAt(1))) return false;

  return true;
};

const formatCPF = (cpf) => {
  cpf = cpf.replace(/[^\d]/g, '');
  if (cpf.length <= 3) return cpf;
  if (cpf.length <= 6) return `${cpf.slice(0, 3)}.${cpf.slice(3)}`;
  if (cpf.length <= 9) return `${cpf.slice(0, 3)}.${cpf.slice(3, 6)}.${cpf.slice(6)}`;
  return `${cpf.slice(0, 3)}.${cpf.slice(3, 6)}.${cpf.slice(6, 9)}-${cpf.slice(9, 11)}`;
};

const formatCNPJ = (cnpj) => {
  cnpj = cnpj.replace(/[^\d]/g, '');
  if (cnpj.length <= 2) return cnpj;
  if (cnpj.length <= 5) return `${cnpj.slice(0, 2)}.${cnpj.slice(2)}`;
  if (cnpj.length <= 8) return `${cnpj.slice(0, 2)}.${cnpj.slice(2, 5)}.${cnpj.slice(5)}`;
  if (cnpj.length <= 12) return `${cnpj.slice(0, 2)}.${cnpj.slice(2, 5)}.${cnpj.slice(5, 8)}/${cnpj.slice(8)}`;
  return `${cnpj.slice(0, 2)}.${cnpj.slice(2, 5)}.${cnpj.slice(5, 8)}/${cnpj.slice(8, 12)}-${cnpj.slice(12, 14)}`;
};

export default function NewPersonPage() {
  const router = useRouter();
  const [formData, setFormData] = useState({
    name: "",
    businessName: "",
    email: "",
    document: "",
    personType: "pf",
    status: "active"
  });
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState("");

  const handleChange = useCallback((e) => {
    const { name, value } = e.target;
    
    if (name === 'document') {
      const formattedValue = formData.personType === 'pf' 
        ? formatCPF(value)
        : formatCNPJ(value);
      setFormData(prev => ({
        ...prev,
        [name]: formattedValue
      }));
    } else if (name === 'personType') {
      setFormData(prev => ({
        ...prev,
        personType: value,
        document: "",
        businessName: value === 'pf' ? "" : prev.businessName
      }));
    } else {
      setFormData(prev => ({
        ...prev,
        [name]: value
      }));
    }
  }, [formData.personType]);

  const validateForm = useCallback(() => {
    if (!formData.name || !formData.email || !formData.document) {
      setError("Por favor, preencha todos os campos obrigatórios");
      return false;
    }
    if (formData.personType === 'pj' && !formData.businessName) {
      setError("Por favor, preencha a razão social");
      return false;
    }
    if (!formData.email.includes("@")) {
      setError("Por favor, insira um email válido");
      return false;
    }
    if (formData.personType === 'pf' && !validateCPF(formData.document)) {
      setError("Por favor, insira um CPF válido");
      return false;
    }
    if (formData.personType === 'pj' && !validateCNPJ(formData.document)) {
      setError("Por favor, insira um CNPJ válido");
      return false;
    }
    return true;
  }, [formData]);

  const handleSubmit = useCallback(async (e) => {
    e.preventDefault();
    setError("");

    if (!validateForm()) {
      return;
    }

    try {
      setIsLoading(true);
      await new Promise(resolve => setTimeout(resolve, 1000));
      
      console.log("Person saved successfully:", formData);
      router.push("/pessoas");
    } catch (error) {
      setError("Ocorreu um erro ao salvar a pessoa");
      console.error("Error saving:", error);
    } finally {
      setIsLoading(false);
    }
  }, [formData, validateForm, router]);

  return (
    <div className="min-h-screen bg-gray-100 dark:bg-gray-900 py-8 px-4 sm:px-6 lg:px-8">
      <div className="max-w-2xl mx-auto">
        <div className="bg-white dark:bg-gray-800 rounded-lg shadow-lg p-6">
          <div className="flex justify-between items-center mb-6">
            <h1 className="text-2xl font-bold text-gray-900 dark:text-white">
              Nova Pessoa
            </h1>
            <button
              onClick={() => router.back()}
              className="text-gray-600 hover:text-gray-900 dark:text-gray-400 dark:hover:text-white"
            >
              Voltar
            </button>
          </div>

          {error && (
            <div className="mb-4 p-4 bg-red-50 dark:bg-red-900/50 border border-red-200 dark:border-red-800 rounded-lg text-sm text-red-600 dark:text-red-400">
              {error}
            </div>
          )}

          <form onSubmit={handleSubmit} className="space-y-6">
            <div>
              <label htmlFor="personType" className="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1">
                Tipo de Pessoa *
              </label>
              <select
                id="personType"
                name="personType"
                value={formData.personType}
                onChange={handleChange}
                disabled={isLoading}
                className="w-full px-4 py-2 border border-gray-300 dark:border-gray-600 rounded-lg focus:ring-2 focus:ring-indigo-500 focus:border-transparent dark:bg-gray-700 dark:text-white disabled:opacity-50"
              >
                <option value="pf">Pessoa Física</option>
                <option value="pj">Pessoa Jurídica</option>
              </select>
            </div>

            <div>
              <label htmlFor="name" className="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1">
                {formData.personType === 'pf' ? 'Nome *' : 'Nome Fantasia *'}
              </label>
              <input
                type="text"
                id="name"
                name="name"
                value={formData.name}
                onChange={handleChange}
                disabled={isLoading}
                className="w-full px-4 py-2 border border-gray-300 dark:border-gray-600 rounded-lg focus:ring-2 focus:ring-indigo-500 focus:border-transparent dark:bg-gray-700 dark:text-white disabled:opacity-50"
                placeholder={formData.personType === 'pf' ? "Digite o nome" : "Digite o nome fantasia"}
              />
            </div>

            {formData.personType === 'pj' && (
              <div>
                <label htmlFor="businessName" className="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1">
                  Razão Social *
                </label>
                <input
                  type="text"
                  id="businessName"
                  name="businessName"
                  value={formData.businessName}
                  onChange={handleChange}
                  disabled={isLoading}
                  className="w-full px-4 py-2 border border-gray-300 dark:border-gray-600 rounded-lg focus:ring-2 focus:ring-indigo-500 focus:border-transparent dark:bg-gray-700 dark:text-white disabled:opacity-50"
                  placeholder="Digite a razão social"
                />
              </div>
            )}

            <div>
              <label htmlFor="email" className="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1">
                Email *
              </label>
              <input
                type="email"
                id="email"
                name="email"
                value={formData.email}
                onChange={handleChange}
                disabled={isLoading}
                className="w-full px-4 py-2 border border-gray-300 dark:border-gray-600 rounded-lg focus:ring-2 focus:ring-indigo-500 focus:border-transparent dark:bg-gray-700 dark:text-white disabled:opacity-50"
                placeholder="Digite o email"
              />
            </div>

            <div>
              <label htmlFor="document" className="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1">
                {formData.personType === 'pf' ? 'CPF *' : 'CNPJ *'}
              </label>
              <input
                type="text"
                id="document"
                name="document"
                value={formData.document}
                onChange={handleChange}
                disabled={isLoading}
                maxLength={formData.personType === 'pf' ? 14 : 18}
                className="w-full px-4 py-2 border border-gray-300 dark:border-gray-600 rounded-lg focus:ring-2 focus:ring-indigo-500 focus:border-transparent dark:bg-gray-700 dark:text-white disabled:opacity-50"
                placeholder={formData.personType === 'pf' ? "000.000.000-00" : "00.000.000/0000-00"}
              />
            </div>

            <div>
              <label htmlFor="status" className="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1">
                Status
              </label>
              <select
                id="status"
                name="status"
                value={formData.status}
                onChange={handleChange}
                disabled={isLoading}
                className="w-full px-4 py-2 border border-gray-300 dark:border-gray-600 rounded-lg focus:ring-2 focus:ring-indigo-500 focus:border-transparent dark:bg-gray-700 dark:text-white disabled:opacity-50"
              >
                <option value="active">Ativo</option>
                <option value="inactive">Inativo</option>
              </select>
            </div>

            <div className="flex justify-end space-x-4">
              <button
                type="button"
                onClick={() => router.back()}
                disabled={isLoading}
                className="px-4 py-2 border border-gray-300 dark:border-gray-600 text-gray-700 dark:text-gray-300 rounded-lg hover:bg-gray-50 dark:hover:bg-gray-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500 disabled:opacity-50"
              >
                Cancelar
              </button>
              <button
                type="submit"
                disabled={isLoading}
                className="px-4 py-2 bg-indigo-600 text-white rounded-lg hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500 disabled:opacity-50"
              >
                {isLoading ? "Salvando..." : "Salvar"}
              </button>
            </div>
          </form>
        </div>
      </div>
    </div>
  );
} 