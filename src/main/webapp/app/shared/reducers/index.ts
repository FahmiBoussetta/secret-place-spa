import { combineReducers } from 'redux';
import { loadingBarReducer as loadingBar } from 'react-redux-loading-bar';

import locale, { LocaleState } from './locale';
import authentication, { AuthenticationState } from './authentication';
import applicationProfile, { ApplicationProfileState } from './application-profile';

import administration, { AdministrationState } from 'app/modules/administration/administration.reducer';
import userManagement, { UserManagementState } from 'app/modules/administration/user-management/user-management.reducer';
import register, { RegisterState } from 'app/modules/account/register/register.reducer';
import activate, { ActivateState } from 'app/modules/account/activate/activate.reducer';
import password, { PasswordState } from 'app/modules/account/password/password.reducer';
import settings, { SettingsState } from 'app/modules/account/settings/settings.reducer';
import passwordReset, { PasswordResetState } from 'app/modules/account/password-reset/password-reset.reducer';
// prettier-ignore
import booking, {
  BookingState
} from 'app/entities/booking/booking.reducer';
// prettier-ignore
import bookingElement, {
  BookingElementState
} from 'app/entities/booking-element/booking-element.reducer';
// prettier-ignore
import favorites, {
  FavoritesState
} from 'app/entities/favorites/favorites.reducer';
// prettier-ignore
import rating, {
  RatingState
} from 'app/entities/rating/rating.reducer';
// prettier-ignore
import establishment, {
  EstablishmentState
} from 'app/entities/establishment/establishment.reducer';
// prettier-ignore
import manage, {
  ManageState
} from 'app/entities/manage/manage.reducer';
// prettier-ignore
import establishmentClosure, {
  EstablishmentClosureState
} from 'app/entities/establishment-closure/establishment-closure.reducer';
// prettier-ignore
import housingTemplate, {
  HousingTemplateState
} from 'app/entities/housing-template/housing-template.reducer';
// prettier-ignore
import housingClosure, {
  HousingClosureState
} from 'app/entities/housing-closure/housing-closure.reducer';
// prettier-ignore
import photo, {
  PhotoState
} from 'app/entities/photo/photo.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

export interface IRootState {
  readonly authentication: AuthenticationState;
  readonly locale: LocaleState;
  readonly applicationProfile: ApplicationProfileState;
  readonly administration: AdministrationState;
  readonly userManagement: UserManagementState;
  readonly register: RegisterState;
  readonly activate: ActivateState;
  readonly passwordReset: PasswordResetState;
  readonly password: PasswordState;
  readonly settings: SettingsState;
  readonly booking: BookingState;
  readonly bookingElement: BookingElementState;
  readonly favorites: FavoritesState;
  readonly rating: RatingState;
  readonly establishment: EstablishmentState;
  readonly manage: ManageState;
  readonly establishmentClosure: EstablishmentClosureState;
  readonly housingTemplate: HousingTemplateState;
  readonly housingClosure: HousingClosureState;
  readonly photo: PhotoState;
  /* jhipster-needle-add-reducer-type - JHipster will add reducer type here */
  readonly loadingBar: any;
}

const rootReducer = combineReducers<IRootState>({
  authentication,
  locale,
  applicationProfile,
  administration,
  userManagement,
  register,
  activate,
  passwordReset,
  password,
  settings,
  booking,
  bookingElement,
  favorites,
  rating,
  establishment,
  manage,
  establishmentClosure,
  housingTemplate,
  housingClosure,
  photo,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
  loadingBar,
});

export default rootReducer;
