import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IFavorites, defaultValue } from 'app/shared/model/favorites.model';

export const ACTION_TYPES = {
  FETCH_FAVORITES_LIST: 'favorites/FETCH_FAVORITES_LIST',
  FETCH_FAVORITES: 'favorites/FETCH_FAVORITES',
  CREATE_FAVORITES: 'favorites/CREATE_FAVORITES',
  UPDATE_FAVORITES: 'favorites/UPDATE_FAVORITES',
  PARTIAL_UPDATE_FAVORITES: 'favorites/PARTIAL_UPDATE_FAVORITES',
  DELETE_FAVORITES: 'favorites/DELETE_FAVORITES',
  RESET: 'favorites/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IFavorites>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type FavoritesState = Readonly<typeof initialState>;

// Reducer

export default (state: FavoritesState = initialState, action): FavoritesState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_FAVORITES_LIST):
    case REQUEST(ACTION_TYPES.FETCH_FAVORITES):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_FAVORITES):
    case REQUEST(ACTION_TYPES.UPDATE_FAVORITES):
    case REQUEST(ACTION_TYPES.DELETE_FAVORITES):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_FAVORITES):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_FAVORITES_LIST):
    case FAILURE(ACTION_TYPES.FETCH_FAVORITES):
    case FAILURE(ACTION_TYPES.CREATE_FAVORITES):
    case FAILURE(ACTION_TYPES.UPDATE_FAVORITES):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_FAVORITES):
    case FAILURE(ACTION_TYPES.DELETE_FAVORITES):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_FAVORITES_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_FAVORITES):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_FAVORITES):
    case SUCCESS(ACTION_TYPES.UPDATE_FAVORITES):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_FAVORITES):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_FAVORITES):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {},
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState,
      };
    default:
      return state;
  }
};

const apiUrl = 'api/favorites';

// Actions

export const getEntities: ICrudGetAllAction<IFavorites> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_FAVORITES_LIST,
  payload: axios.get<IFavorites>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IFavorites> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_FAVORITES,
    payload: axios.get<IFavorites>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IFavorites> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_FAVORITES,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IFavorites> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_FAVORITES,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IFavorites> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_FAVORITES,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IFavorites> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_FAVORITES,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
