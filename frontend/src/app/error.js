"use client";

import { useEffect } from "react";
import Link from "next/link";

export default function Error({
  error,
  reset,
}) {
  useEffect(() => {
    console.error(error);
  }, [error]);

  return (
    <div className="min-h-screen flex items-center justify-center bg-gradient-to-br from-gray-50 to-gray-100 dark:from-gray-900 dark:to-gray-800 py-12 px-4 sm:px-6 lg:px-8">
      <div className="max-w-md w-full space-y-8 bg-white dark:bg-gray-800 p-8 rounded-xl shadow-lg text-center">
        <div className="space-y-4">
          <h1 className="text-9xl font-extrabold text-red-600 dark:text-red-400">500</h1>
          <h2 className="text-3xl font-bold text-gray-900 dark:text-white">Erro interno do servidor</h2>
          <p className="text-gray-600 dark:text-gray-400">
            Desculpe, algo deu errado. Nossa equipe foi notificada e está trabalhando para resolver o problema.
          </p>
        </div>
        
        <div className="flex flex-col space-y-4">
          <button
            onClick={() => reset()}
            className="inline-flex items-center justify-center px-6 py-3 border border-transparent text-base font-medium rounded-lg text-white bg-red-600 hover:bg-red-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-red-500 transition-all duration-200 ease-in-out transform hover:scale-[1.02] active:scale-[0.98]"
          >
            Tentar novamente
          </button>
          <Link 
            href="/"
            className="inline-flex items-center justify-center px-6 py-3 border border-gray-300 dark:border-gray-600 text-base font-medium rounded-lg text-gray-700 dark:text-gray-300 bg-white dark:bg-gray-700 hover:bg-gray-50 dark:hover:bg-gray-600 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-red-500 transition-all duration-200 ease-in-out"
          >
            Voltar para a página inicial
          </Link>
        </div>
      </div>
    </div>
  );
} 