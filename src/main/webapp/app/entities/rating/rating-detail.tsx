import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, UncontrolledTooltip, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './rating.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IRatingDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const RatingDetail = (props: IRatingDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { ratingEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="ratingDetailsHeading">
          <Translate contentKey="secretplacespaApp.rating.detail.title">Rating</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{ratingEntity.id}</dd>
          <dt>
            <span id="rate">
              <Translate contentKey="secretplacespaApp.rating.rate">Rate</Translate>
            </span>
            <UncontrolledTooltip target="rate">
              <Translate contentKey="secretplacespaApp.rating.help.rate" />
            </UncontrolledTooltip>
          </dt>
          <dd>{ratingEntity.rate}</dd>
          <dt>
            <span id="comment">
              <Translate contentKey="secretplacespaApp.rating.comment">Comment</Translate>
            </span>
            <UncontrolledTooltip target="comment">
              <Translate contentKey="secretplacespaApp.rating.help.comment" />
            </UncontrolledTooltip>
          </dt>
          <dd>{ratingEntity.comment}</dd>
          <dt>
            <span id="ratingDate">
              <Translate contentKey="secretplacespaApp.rating.ratingDate">Rating Date</Translate>
            </span>
            <UncontrolledTooltip target="ratingDate">
              <Translate contentKey="secretplacespaApp.rating.help.ratingDate" />
            </UncontrolledTooltip>
          </dt>
          <dd>{ratingEntity.ratingDate ? <TextFormat value={ratingEntity.ratingDate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <Translate contentKey="secretplacespaApp.rating.userId">User Id</Translate>
          </dt>
          <dd>{ratingEntity.userId ? ratingEntity.userId.id : ''}</dd>
          <dt>
            <Translate contentKey="secretplacespaApp.rating.establishmentId">Establishment Id</Translate>
          </dt>
          <dd>{ratingEntity.establishmentId ? ratingEntity.establishmentId.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/rating" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/rating/${ratingEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ rating }: IRootState) => ({
  ratingEntity: rating.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(RatingDetail);
