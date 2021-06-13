import { IHousingTemplate } from 'app/shared/model/housing-template.model';
import { IEstablishment } from 'app/shared/model/establishment.model';

export interface IPhoto {
  id?: number;
  name?: string;
  housingTemplateId?: IHousingTemplate | null;
  establishmentId?: IEstablishment | null;
}

export const defaultValue: Readonly<IPhoto> = {};
