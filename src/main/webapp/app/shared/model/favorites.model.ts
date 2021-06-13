import { IUser } from 'app/shared/model/user.model';
import { IEstablishment } from 'app/shared/model/establishment.model';

export interface IFavorites {
  id?: number;
  userId?: IUser | null;
  establishmentId?: IEstablishment | null;
}

export const defaultValue: Readonly<IFavorites> = {};
