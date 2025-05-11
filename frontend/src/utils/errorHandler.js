export class AppError extends Error {
  constructor(message, code, details = null) {
    super(message);
    this.name = 'AppError';
    this.code = code;
    this.details = details;
  }
}

export const ErrorCodes = {
  VALIDATION_ERROR: 'VALIDATION_ERROR',
  NETWORK_ERROR: 'NETWORK_ERROR',
  SERVER_ERROR: 'SERVER_ERROR',
  UNAUTHORIZED: 'UNAUTHORIZED',
  NOT_FOUND: 'NOT_FOUND',
  UNKNOWN_ERROR: 'UNKNOWN_ERROR'
};

export const handleError = (error) => {
  if (error instanceof AppError) {
    return {
      message: error.message,
      code: error.code,
      details: error.details
    };
  }

  if (!error.response) {
    return {
      message: 'Erro de conexão. Verifique sua internet e tente novamente.',
      code: ErrorCodes.NETWORK_ERROR
    };
  }

  const status = error.response?.status;
  const data = error.response?.data;

  switch (status) {
    case 400:
      return {
        message: data?.message || 'Dados inválidos. Verifique os campos e tente novamente.',
        code: ErrorCodes.VALIDATION_ERROR,
        details: data?.details
      };
    case 401:
      return {
        message: 'Sessão expirada. Por favor, faça login novamente.',
        code: ErrorCodes.UNAUTHORIZED
      };
    case 403:
      return {
        message: 'Você não tem permissão para realizar esta ação.',
        code: ErrorCodes.UNAUTHORIZED
      };
    case 404:
      return {
        message: 'Recurso não encontrado.',
        code: ErrorCodes.NOT_FOUND
      };
    case 500:
      return {
        message: 'Erro interno do servidor. Tente novamente mais tarde.',
        code: ErrorCodes.SERVER_ERROR
      };
    default:
      return {
        message: 'Ocorreu um erro inesperado. Tente novamente mais tarde.',
        code: ErrorCodes.UNKNOWN_ERROR
      };
  }
}; 