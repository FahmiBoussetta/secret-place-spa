import { IEstablishment } from 'app/shared/model/establishment.model';
import { HousingType } from 'app/shared/model/enumerations/housing-type.model';

export interface IHousingTemplate {
  id?: number;
  housingType?: HousingType;
  nbOfUnit?: number;
  nbMaxOfOccupants?: number;
  price?: number;
  establishmentId?: IEstablishment | null;
}

export const defaultValue: Readonly<IHousingTemplate> = {};
