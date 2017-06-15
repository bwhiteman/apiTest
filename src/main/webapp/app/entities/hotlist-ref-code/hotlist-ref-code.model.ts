import { Hotlist } from '../hotlist';
export class HotlistRefCode {
    constructor(
        public id?: number,
        public code?: string,
        public name?: string,
        public dbKey?: string,
        public hotlist?: Hotlist,
    ) {
    }
}
