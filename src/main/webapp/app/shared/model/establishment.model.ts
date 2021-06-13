import { EstablishmentType } from 'app/shared/model/enumerations/establishment-type.model';

export interface IEstablishment {
  id?: number;
  name?: string;
  address?: string;
  latitude?: number | null;
  longitude?: number | null;
  globalRate?: number | null;
  establishmentType?: EstablishmentType;
  hasParking?: boolean;
  hasRestaurant?: boolean;
  hasFreeWifi?: boolean;
  description?: string | null;
}

export const defaultValue: Readonly<IEstablishment> = {
  hasParking: false,
  hasRestaurant: false,
  hasFreeWifi: false,
};
