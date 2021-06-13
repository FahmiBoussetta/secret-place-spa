import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IManage, defaultValue } from 'app/shared/model/manage.model';

export const ACTION_TYPES = {
  FETCH_MANAGE_LIST: 'manage/FETCH_MANAGE_LIST',
  FETCH_MANAGE: 'manage/FETCH_MANAGE',
  CREATE_MANAGE: 'manage/CREATE_MANAGE',
  UPDATE_MANAGE: 'manage/UPDATE_MANAGE',
  PARTIAL_UPDATE_MANAGE: 'manage/PARTIAL_UPDATE_MANAGE',
  DELETE_MANAGE: 'manage/DELETE_MANAGE',
  RESET: 'manage/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IManage>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type ManageState = Readonly<typeof initialState>;

// Reducer

export default (state: ManageState = initialState, action): ManageState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_MANAGE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_MANAGE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_MANAGE):
    case REQUEST(ACTION_TYPES.UPDATE_MANAGE):
    case REQUEST(ACTION_TYPES.DELETE_MANAGE):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_MANAGE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_MANAGE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_MANAGE):
    case FAILURE(ACTION_TYPES.CREATE_MANAGE):
    case FAILURE(ACTION_TYPES.UPDATE_MANAGE):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_MANAGE):
    case FAILURE(ACTION_TYPES.DELETE_MANAGE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_MANAGE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_MANAGE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_MANAGE):
    case SUCCESS(ACTION_TYPES.UPDATE_MANAGE):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_MANAGE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_MANAGE):
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

const apiUrl = 'api/manages';

// Actions

export const getEntities: ICrudGetAllAction<IManage> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_MANAGE_LIST,
  payload: axios.get<IManage>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IManage> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_MANAGE,
    payload: axios.get<IManage>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IManage> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_MANAGE,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IManage> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_MANAGE,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IManage> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_MANAGE,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IManage> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_MANAGE,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
