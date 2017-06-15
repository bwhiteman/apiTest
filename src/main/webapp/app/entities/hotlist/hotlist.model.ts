import { HotlistRefCode } from '../hotlist-ref-code';
export class Hotlist {
    constructor(
        public id?: number,
        public value?: string,
        public dbKey?: string,
        public hotlistRefCode?: HotlistRefCode,
    ) {
    }
}
