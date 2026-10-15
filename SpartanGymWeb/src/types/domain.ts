/** Modelos iniciales para la migración gradual a React + TypeScript. */
export type Rol =
  | 'ROLE_SUPERADMIN'
  | 'ROLE_ADMIN'
  | 'ROLE_RECEPCIONISTA'
  | 'ROLE_ENTRENADOR'
  | 'ROLE_SOCIO';

export interface SesionUsuario {
  id: string;
  email: string;
  rol: Rol;
  token: string;
}

export interface ApiProblem {
  type?: string;
  title?: string;
  status: number;
  detail?: string;
  codigo?: string | null;
}

export type Identificador = string;

export interface SucursalResumen {
  id: Identificador;
  nombre: string;
  ubicacion: string;
  estado: string;
}
