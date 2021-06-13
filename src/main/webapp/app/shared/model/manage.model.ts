import { IUser } from 'app/shared/model/user.model';
import { IEstablishment } from 'app/shared/model/establishment.model';

export interface IManage {
  id?: number;
  userId?: IUser | null;
  establishmentId?: IEstablishment | null;
}

export const defaultValue: Readonly<IManage> = {};
