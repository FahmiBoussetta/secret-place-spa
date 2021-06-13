import { IUser } from 'app/shared/model/user.model';
import { IEstablishment } from 'app/shared/model/establishment.model';

export interface IBooking {
  id?: number;
  email?: string;
  totalPrice?: number;
  comment?: string | null;
  validate?: boolean;
  paypalLink?: string | null;
  jhiUserId?: IUser | null;
  establishmentId?: IEstablishment | null;
}

export const defaultValue: Readonly<IBooking> = {
  validate: false,
};
