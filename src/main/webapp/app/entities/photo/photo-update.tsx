import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label, UncontrolledTooltip } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IHousingTemplate } from 'app/shared/model/housing-template.model';
import { getEntities as getHousingTemplates } from 'app/entities/housing-template/housing-template.reducer';
import { IEstablishment } from 'app/shared/model/establishment.model';
import { getEntities as getEstablishments } from 'app/entities/establishment/establishment.reducer';
import { getEntity, updateEntity, createEntity, reset } from './photo.reducer';
import { IPhoto } from 'app/shared/model/photo.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IPhotoUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const PhotoUpdate = (props: IPhotoUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { photoEntity, housingTemplates, establishments, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/photo');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getHousingTemplates();
    props.getEstablishments();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...photoEntity,
        ...values,
        housingTemplateId: housingTemplates.find(it => it.id.toString() === values.housingTemplateIdId.toString()),
        establishmentId: establishments.find(it => it.id.toString() === values.establishmentIdId.toString()),
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="secretplacespaApp.photo.home.createOrEditLabel" data-cy="PhotoCreateUpdateHeading">
            <Translate contentKey="secretplacespaApp.photo.home.createOrEditLabel">Create or edit a Photo</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : photoEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="photo-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="photo-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="nameLabel" for="photo-name">
                  <Translate contentKey="secretplacespaApp.photo.name">Name</Translate>
                </Label>
                <AvField
                  id="photo-name"
                  data-cy="name"
                  type="text"
                  name="name"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                    minLength: { value: 1, errorMessage: translate('entity.validation.minlength', { min: 1 }) },
                  }}
                />
                <UncontrolledTooltip target="nameLabel">
                  <Translate contentKey="secretplacespaApp.photo.help.name" />
                </UncontrolledTooltip>
              </AvGroup>
              <AvGroup>
                <Label for="photo-housingTemplateId">
                  <Translate contentKey="secretplacespaApp.photo.housingTemplateId">Housing Template Id</Translate>
                </Label>
                <AvInput
                  id="photo-housingTemplateId"
                  data-cy="housingTemplateId"
                  type="select"
                  className="form-control"
                  name="housingTemplateIdId"
                >
                  <option value="" key="0" />
                  {housingTemplates
                    ? housingTemplates.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="photo-establishmentId">
                  <Translate contentKey="secretplacespaApp.photo.establishmentId">Establishment Id</Translate>
                </Label>
                <AvInput
                  id="photo-establishmentId"
                  data-cy="establishmentId"
                  type="select"
                  className="form-control"
                  name="establishmentIdId"
                >
                  <option value="" key="0" />
                  {establishments
                    ? establishments.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/photo" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  housingTemplates: storeState.housingTemplate.entities,
  establishments: storeState.establishment.entities,
  photoEntity: storeState.photo.entity,
  loading: storeState.photo.loading,
  updating: storeState.photo.updating,
  updateSuccess: storeState.photo.updateSuccess,
});

const mapDispatchToProps = {
  getHousingTemplates,
  getEstablishments,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(PhotoUpdate);
