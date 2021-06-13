import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IEstablishment } from 'app/shared/model/establishment.model';
import { getEntities as getEstablishments } from 'app/entities/establishment/establishment.reducer';
import { getEntity, updateEntity, createEntity, reset } from './establishment-closure.reducer';
import { IEstablishmentClosure } from 'app/shared/model/establishment-closure.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IEstablishmentClosureUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const EstablishmentClosureUpdate = (props: IEstablishmentClosureUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { establishmentClosureEntity, establishments, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/establishment-closure');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getEstablishments();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    values.startDate = convertDateTimeToServer(values.startDate);
    values.endDate = convertDateTimeToServer(values.endDate);

    if (errors.length === 0) {
      const entity = {
        ...establishmentClosureEntity,
        ...values,
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
          <h2 id="secretplacespaApp.establishmentClosure.home.createOrEditLabel" data-cy="EstablishmentClosureCreateUpdateHeading">
            <Translate contentKey="secretplacespaApp.establishmentClosure.home.createOrEditLabel">
              Create or edit a EstablishmentClosure
            </Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : establishmentClosureEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="establishment-closure-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="establishment-closure-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="startDateLabel" for="establishment-closure-startDate">
                  <Translate contentKey="secretplacespaApp.establishmentClosure.startDate">Start Date</Translate>
                </Label>
                <AvInput
                  id="establishment-closure-startDate"
                  data-cy="startDate"
                  type="datetime-local"
                  className="form-control"
                  name="startDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.establishmentClosureEntity.startDate)}
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="endDateLabel" for="establishment-closure-endDate">
                  <Translate contentKey="secretplacespaApp.establishmentClosure.endDate">End Date</Translate>
                </Label>
                <AvInput
                  id="establishment-closure-endDate"
                  data-cy="endDate"
                  type="datetime-local"
                  className="form-control"
                  name="endDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.establishmentClosureEntity.endDate)}
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="causeLabel" for="establishment-closure-cause">
                  <Translate contentKey="secretplacespaApp.establishmentClosure.cause">Cause</Translate>
                </Label>
                <AvField
                  id="establishment-closure-cause"
                  data-cy="cause"
                  type="text"
                  name="cause"
                  validate={{
                    maxLength: { value: 250, errorMessage: translate('entity.validation.maxlength', { max: 250 }) },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label for="establishment-closure-establishmentId">
                  <Translate contentKey="secretplacespaApp.establishmentClosure.establishmentId">Establishment Id</Translate>
                </Label>
                <AvInput
                  id="establishment-closure-establishmentId"
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
              <Button tag={Link} id="cancel-save" to="/establishment-closure" replace color="info">
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
  establishments: storeState.establishment.entities,
  establishmentClosureEntity: storeState.establishmentClosure.entity,
  loading: storeState.establishmentClosure.loading,
  updating: storeState.establishmentClosure.updating,
  updateSuccess: storeState.establishmentClosure.updateSuccess,
});

const mapDispatchToProps = {
  getEstablishments,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(EstablishmentClosureUpdate);
