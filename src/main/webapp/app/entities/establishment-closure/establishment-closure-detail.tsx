import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './establishment-closure.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IEstablishmentClosureDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const EstablishmentClosureDetail = (props: IEstablishmentClosureDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { establishmentClosureEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="establishmentClosureDetailsHeading">
          <Translate contentKey="secretplacespaApp.establishmentClosure.detail.title">EstablishmentClosure</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{establishmentClosureEntity.id}</dd>
          <dt>
            <span id="startDate">
              <Translate contentKey="secretplacespaApp.establishmentClosure.startDate">Start Date</Translate>
            </span>
          </dt>
          <dd>
            {establishmentClosureEntity.startDate ? (
              <TextFormat value={establishmentClosureEntity.startDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="endDate">
              <Translate contentKey="secretplacespaApp.establishmentClosure.endDate">End Date</Translate>
            </span>
          </dt>
          <dd>
            {establishmentClosureEntity.endDate ? (
              <TextFormat value={establishmentClosureEntity.endDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="cause">
              <Translate contentKey="secretplacespaApp.establishmentClosure.cause">Cause</Translate>
            </span>
          </dt>
          <dd>{establishmentClosureEntity.cause}</dd>
          <dt>
            <Translate contentKey="secretplacespaApp.establishmentClosure.establishmentId">Establishment Id</Translate>
          </dt>
          <dd>{establishmentClosureEntity.establishmentId ? establishmentClosureEntity.establishmentId.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/establishment-closure" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/establishment-closure/${establishmentClosureEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ establishmentClosure }: IRootState) => ({
  establishmentClosureEntity: establishmentClosure.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(EstablishmentClosureDetail);
